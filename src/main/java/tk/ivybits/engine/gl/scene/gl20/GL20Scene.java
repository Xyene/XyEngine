/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.scene.IEnvironmentMap;
import tk.ivybits.engine.gl.scene.PriorityComparableDrawable;
import tk.ivybits.engine.gl.scene.gl20.aa.MSAAFBO;
import tk.ivybits.engine.gl.scene.gl20.bloom.BloomEffect;
import tk.ivybits.engine.gl.scene.gl20.shader.BaseShader;
import tk.ivybits.engine.gl.scene.gl20.shader.PhongLightingShader;
import tk.ivybits.engine.gl.scene.gl20.shader.RawRenderShader;
import tk.ivybits.engine.gl.texture.Framebuffer;
import tk.ivybits.engine.gl.texture.RenderBuffer;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.Frustum;
import tk.ivybits.engine.scene.camera.BasicCamera;
import tk.ivybits.engine.scene.event.SceneChangeAdapter;
import tk.ivybits.engine.scene.node.*;
import tk.ivybits.engine.util.FrameTimer;

import static tk.ivybits.engine.gl.GL.*;

import java.util.*;

import static tk.ivybits.engine.scene.IDrawContext.Capability.*;

public class GL20Scene implements IScene {
    private BloomEffect bloomEffect;
    GL20DrawContext drawContext;
    PriorityQueue<PriorityComparableDrawable> tracker = new PriorityQueue<>(1, PriorityComparableDrawable.COMPARATOR);
    private BasicCamera camera;
    BaseShader lightingShader;
    private int viewWidth;
    private int viewHeight;
    private RawRenderShader rawGeometryShader;
    private HashMap<Framebuffer, Matrix4f> shadowMapBuffers = new HashMap<>();
    /**
     * Reference for GL20Tesselator - vertex attribute offsets
     */
    BaseShader currentGeometryShader;
    private ISceneGraph sceneGraph;
    private MSAAFBO msaaBuffer;
    private Frustum frustum = new Frustum();

    public int drawn = 0;
    private IEnvironmentMap envMap;

    public GL20Scene(int viewWidth, int viewHeight, ISceneGraph sceneGraph) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.sceneGraph = sceneGraph;
        lightingShader = new PhongLightingShader(this, shadowMapBuffers);
        currentGeometryShader = lightingShader;

        sceneGraph.addSceneChangeListener(new SceneChangeAdapter() {
            @Override
            public void actorAdded(IActor actor) {
                IDrawable draw = actor.createDrawable(drawContext);
                tracker.add(new PriorityComparableDrawable(actor, draw));
            }

            @Override
            public void actorRemoved(IActor actor) {
                Iterator<PriorityComparableDrawable> i = tracker.iterator();
                while (i.hasNext()) {
                    PriorityComparableDrawable pcb = i.next();
                    if (pcb == actor) {
                        i.remove();
                        break;
                    }
                }
            }
        }, true);
        sceneGraph.addSceneChangeListener(lightingShader, true);
        camera = new BasicCamera(this)
                .setAspectRatio(viewWidth / (float) viewHeight)
                .setFieldOfView(60)
                .setClip(Float.MAX_VALUE, 0.3f);
    }

    @Override
    public ISceneGraph getSceneGraph() {
        return sceneGraph;
    }

    @Override
    public IDrawContext getDrawContext() {
        return drawContext != null ? drawContext : (drawContext = new GL20DrawContext(this));
    }

    @Override
    public void setViewportSize(int width, int height) {
        for (Framebuffer fbo : shadowMapBuffers.keySet()) fbo.resize(width, height);
        if (msaaBuffer != null) msaaBuffer.resize(width, height);
        if (bloomEffect != null) bloomEffect.resize(width, height);
        camera.setAspectRatio(width / (float) height);
        this.viewWidth = width;
        this.viewHeight = height;
    }

    private void generateShadowMaps() {
        if (rawGeometryShader == null) {
            rawGeometryShader = new RawRenderShader(this, shadowMapBuffers);
        }

        currentGeometryShader = rawGeometryShader;
        List<ISpotLight> spotLights = sceneGraph.getRoot().getSpotLights();
        int numLights = spotLights.size();
        if (shadowMapBuffers.size() < numLights) {
            for (int n = shadowMapBuffers.size(); n < numLights; n++) {
                Framebuffer self;
                shadowMapBuffers.put(
                        self = new Framebuffer(viewWidth, viewHeight)
                                .attach(RenderBuffer.newDepthBuffer(viewWidth, viewHeight))
                                .attach(new Texture(GL_TEXTURE_2D, GL_DEPTH_COMPONENT, viewWidth, viewHeight)),
                        new Matrix4f()
                );
            }
        } else if (shadowMapBuffers.size() > numLights) {
            Iterator<Map.Entry<Framebuffer, Matrix4f>> iterator = shadowMapBuffers.entrySet().iterator();
            for (int n = 0; n < shadowMapBuffers.size(); n++) {
                iterator.next();
                iterator.remove();
            }
        }

        Iterator<Framebuffer> maps = shadowMapBuffers.keySet().iterator();
        for (int n = 0; n < numLights; n++) {
            camera.pushMatrix();
            ISpotLight light = spotLights.get(n);

            Framebuffer fbo = maps.next();
            fbo.bind();

            glClear(GL_DEPTH_BUFFER_BIT); // Clear only depth buffer
            glLoadIdentity();

            rawGeometryShader.setProjectionTransform(getProjection().getProjectionTransform());

            rawGeometryShader.getProgram().attach();

            camera.setRotation(light.pitch(), light.yaw(), 0);
            camera.setPosition(light.x(), light.y(), light.z());

            shadowMapBuffers.put(fbo, getProjection().getViewTransform());
            glEnable(GL_CULL_FACE);
            glCullFace(GL_FRONT); // Avoid self-shadowing
            for (PriorityComparableDrawable entity : tracker) {
                rawGeometryShader.setModelTransform(entity.actor.getTransform());
                entity.drawable.draw(this);
            }
            glDisable(GL_CULL_FACE);
            rawGeometryShader.getProgram().detach();

            fbo.unbind();
            camera.popMatrix();
        }
    }

    protected void _draw() {
        currentGeometryShader = lightingShader;
        if (lightingShader != null) lightingShader.getProgram().attach();

        List<PriorityComparableDrawable> visible = new ArrayList<>();
        float delta = timer.getDelta();
        for (PriorityComparableDrawable entity : tracker) {
            entity.actor.update(delta); // TODO: real delta
            if (frustum.bbInFrustum(entity.actor.x(), entity.actor.y(), entity.actor.z(), entity.actor.getBoundingBox())) {
                visible.add(entity);
            }
        }

        if (drawContext.isEnabled(ALPHA_TESTING)) {
            glDisable(GL_CULL_FACE);
            for (PriorityComparableDrawable entity : visible) {
                if (entity.drawable.isTransparent()) continue;
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                currentGeometryShader.setEnvironmentMap(envMap.getFor(this, entity.actor));
                entity.drawable.draw(this);
            }

            glEnable(GL_CULL_FACE);

            glCullFace(GL_FRONT);
            for (PriorityComparableDrawable entity : visible) {
                if (!entity.drawable.isTransparent()) continue;
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                currentGeometryShader.setEnvironmentMap(envMap.getFor(this, entity.actor));
                entity.drawable.draw(this);
            }
            glCullFace(GL_BACK);
            for (PriorityComparableDrawable entity : visible) {
                if (!entity.drawable.isTransparent()) continue;
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                currentGeometryShader.setEnvironmentMap(envMap.getFor(this, entity.actor));
                entity.drawable.draw(this);
                drawn++;
            }

            glDisable(GL_CULL_FACE);
        } else {
            glDisable(GL_CULL_FACE);
            for (PriorityComparableDrawable entity : visible) {
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                currentGeometryShader.setEnvironmentMap(envMap.getFor(this, entity.actor));
                entity.drawable.draw(this);
            }
        }

        drawn = visible.size();

        if (lightingShader != null) lightingShader.getProgram().detach();
    }

    private FrameTimer timer;

    @Override
    public void draw() {
        if (timer == null) {
            timer = new FrameTimer();
            timer.start();
        } else
            timer.update();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        if (drawContext.isEnabled(OBJECT_SHADOWS)) {
            glPushAttrib(GL_ALL_ATTRIB_BITS);
            generateShadowMaps();
            glPopAttrib();
        } else if (shadowMapBuffers.size() > 0) {
            Iterator<Map.Entry<Framebuffer, Matrix4f>> iterator = shadowMapBuffers.entrySet().iterator();
            for (int n = 0; n < shadowMapBuffers.size(); n++) {
                iterator.next();
                iterator.remove();
            }
        }

        boolean antialiasing = drawContext.isEnabled(ANTIALIASING);

        if (antialiasing && msaaBuffer == null) {
            msaaBuffer = new MSAAFBO(viewWidth, viewHeight, getDrawContext().getOption(Key.AA_SAMPLE_COUNT));
            glEnable(GL_MULTISAMPLE);
            glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        } else if (!antialiasing && msaaBuffer != null) {
            msaaBuffer.destroy();
            msaaBuffer = null;
            glDisable(GL_MULTISAMPLE);
            glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        }
        if (antialiasing) {
            if(msaaBuffer.getSamples() != getDrawContext().getOption(Key.AA_SAMPLE_COUNT))
                msaaBuffer.setSamples(getDrawContext().getOption(Key.AA_SAMPLE_COUNT));
            msaaBuffer.bindFramebuffer();
        }

        boolean bloom = drawContext.isEnabled(BLOOM);
        if (bloom && bloomEffect == null) {
            bloomEffect = new BloomEffect(this, viewWidth, viewHeight, 4, 0.5f);
        }
        if (!bloom && bloomEffect != null) {
            bloomEffect.destroy();
            bloomEffect = null;
        }

        if (bloom && !antialiasing) {
            bloomEffect.getInputBuffer().bind();
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        _draw();

        if (antialiasing) {
            msaaBuffer.unbindFramebuffer();
            if (bloom) {
                msaaBuffer.blit(bloomEffect.getInputBuffer().id());
            } else {
                msaaBuffer.blit();
            }
        }

        if (bloom) {
            if (!antialiasing) {
                bloomEffect.getInputBuffer().bind();
            }

            bloomEffect.process();

            glDisable(GL_DEPTH_TEST);

            bloomEffect.getOutputBuffer().bind(0);

            ImmediateProjection.toOrthographicProjection(0, 0, viewWidth, viewHeight);
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(0, 1);
            glVertex2f(0, viewHeight);
            glTexCoord2f(1, 1);
            glVertex2f(viewWidth, viewHeight);
            glTexCoord2f(1, 0);
            glVertex2f(viewWidth, 0);
            glEnd();

            bloomEffect.getOutputBuffer().unbind();

            glEnable(GL_DEPTH_TEST);
        }

        glPopAttrib();
    }

    private Projection projection = new Projection() {
        @Override
        public void setViewTransform(Matrix4f viewMatrix) {
            super.setViewTransform(viewMatrix);
            currentGeometryShader.setViewTransform(viewMatrix);
            frustum.setTransform(projectionMatrix, viewMatrix);
        }

        @Override
        public void setProjectionTransform(Matrix4f projectionMatrix) {
            super.setProjectionTransform(projectionMatrix);
            currentGeometryShader.setProjectionTransform(projectionMatrix);
            frustum.setTransform(projectionMatrix, viewMatrix);
        }

        @Override
        public void setEyePosition(Vector3f eye) {
            super.setEyePosition(eye);
            currentGeometryShader.setEyePosition(eye);
        }
    };

    @Override
    public Projection getProjection() {
        return projection;
    }

    @Override
    public void setEnvironmentMap(IEnvironmentMap environmentMap) {
        this.envMap = environmentMap;
    }
}
