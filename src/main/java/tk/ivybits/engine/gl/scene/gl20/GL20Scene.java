package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.scene.PriorityComparableDrawable;
import tk.ivybits.engine.gl.scene.gl20.shader.PhongLightingShader;
import tk.ivybits.engine.gl.scene.gl20.shader.RawRenderShader;
import tk.ivybits.engine.gl.scene.gl20.shadow.ShadowMapFBO;
import tk.ivybits.engine.gl.scene.gl20.shader.ISceneShader;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.camera.SimpleCamera;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.node.*;

import static tk.ivybits.engine.gl.GL.*;

import java.util.*;

import static tk.ivybits.engine.scene.IDrawContext.*;

public class GL20Scene implements IScene {
    GL20DrawContext drawContext = new GL20DrawContext(this);
    PriorityQueue<PriorityComparableDrawable> tracker = new PriorityQueue<>(1, PriorityComparableDrawable.COMPARATOR);
    private Projection proj = new Projection();
    private ICamera camera = new SimpleCamera(proj);
    PhongLightingShader lightingShader;
    private int viewWidth;
    private int viewHeight;
    private RawRenderShader rawGeometryShader;
    private BloomShader bloomShader;
    private List<ShadowMapFBO> shadowMapFBOs = new ArrayList<>();
    /**
     * Reference for GL20Tesselator - vertex attribute offsets
     */
    ISceneShader currentGeometryShader;
    private ISceneGraph sceneGraph;
    private MSAAFBO msaaBuffer;
    private FrameBuffer bloomBuffer;

    public GL20Scene(int viewWidth, int viewHeight, ISceneGraph sceneGraph) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.sceneGraph = sceneGraph;
        lightingShader = new PhongLightingShader(this, shadowMapFBOs);

        sceneGraph.addSceneChangeListener(new SceneChangeAdapter() {
            @Override
            public void actorAdded(IActor actor) {
                IDrawable draw = actor.createDrawable(drawContext);
                tracker.add(new PriorityComparableDrawable(actor, draw, draw.priority()));
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
    }

    @Override
    public ISceneGraph getSceneGraph() {
        return sceneGraph;
    }

    @Override
    public IDrawContext getDrawContext() {
        return drawContext;
    }

    @Override
    public ICamera getCamera() {
        return camera;
    }

    @Override
    public void setViewportSize(int width, int height) {
        for (ShadowMapFBO fbo : shadowMapFBOs) fbo.resize(width, height);
        if (msaaBuffer != null) msaaBuffer.resize(width, height);
        if (bloomBuffer != null) bloomBuffer.resize(width, height);
        this.viewWidth = width;
        this.viewHeight = height;
    }

    void destroyShadowMaps() {
        for (int n = 0; n < shadowMapFBOs.size(); n++) {
            shadowMapFBOs.remove(0).destroy();
        }
    }

    private void generateShadowMaps() {
        Projection camera = proj;

        if (rawGeometryShader == null) {
            rawGeometryShader = new RawRenderShader();
        }

        currentGeometryShader = rawGeometryShader;
        List<ISpotLight> spotLights = sceneGraph.getRoot().getSpotLights();
        int numLights = spotLights.size();
        if (shadowMapFBOs.size() < numLights) {
            for (int n = shadowMapFBOs.size(); n < numLights; n++) {
                shadowMapFBOs.add(new ShadowMapFBO(viewWidth, viewHeight));
            }
        } else if (shadowMapFBOs.size() > numLights) {
            for (int n = numLights; n < shadowMapFBOs.size(); n++) {
                shadowMapFBOs.remove(0).destroy();
            }
        }

        for (int n = 0; n < numLights; n++) {
            proj = new Projection();
            ISpotLight light = spotLights.get(n);

            ShadowMapFBO fbo = shadowMapFBOs.get(n);
            fbo.bindForWriting();

            rawGeometryShader.attach();

            glClear(GL_DEPTH_BUFFER_BIT); // Clear only depth buffer
            glLoadIdentity();

            proj.setProjectionMatrix(camera.getProjectionMatrix());

            proj.resetViewMatrix();
            proj.rotateCamera(light.pitch(), light.yaw(), 0);
            proj.translateCamera(light.x(), light.y(), light.z());

            fbo.projection = proj.getViewMatrix();
            glEnable(GL_CULL_FACE);
            glCullFace(GL_FRONT); // Avoid self-shadowing
            for (PriorityComparableDrawable entity : tracker) {
                currentGeometryShader.setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
            glDisable(GL_CULL_FACE);
            rawGeometryShader.detach();

            fbo.unbind();
        }
        proj = camera;
    }

    protected void _draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        currentGeometryShader = lightingShader;
        if (lightingShader != null) lightingShader.attach();

        if (drawContext.isEnabled(ALPHA_TESTING)) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_FRONT);
            for (PriorityComparableDrawable entity : tracker) {
                if (!entity.draw.isTransparent()) continue;
                currentGeometryShader.setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
            glCullFace(GL_BACK);
            for (PriorityComparableDrawable entity : tracker) {
                if (!entity.draw.isTransparent()) continue;
                currentGeometryShader.setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
            glDisable(GL_CULL_FACE);
            for (PriorityComparableDrawable entity : tracker) {
                if (entity.draw.isTransparent()) continue;
                currentGeometryShader.setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
        } else {
            for (PriorityComparableDrawable entity : tracker) {
                currentGeometryShader.setProjection(proj.setModelMatrix(entity.wrapped.getModelMatrix()));
                entity.draw.draw(this);
            }
        }

        if (lightingShader != null) lightingShader.detach();
    }

    @Override
    public void draw() {
        if (drawContext.isEnabled(OBJECT_SHADOWS)) {
            generateShadowMaps();
        }

        boolean antialiasing = drawContext.isEnabled(IDrawContext.ANTIALIASING);

        if (antialiasing && msaaBuffer == null) {
            msaaBuffer = new MSAAFBO(viewWidth, viewHeight, 4);
        } else if (!antialiasing && msaaBuffer != null) {
            msaaBuffer.destroy();
            msaaBuffer = null;
        }
        if (antialiasing) {
            glEnable(GL_MULTISAMPLE);

            msaaBuffer.bind();
        }

        boolean bloom = drawContext.isEnabled(IDrawContext.BLOOM);
        if (bloom && bloomShader == null) {
            bloomShader = new BloomShader();
            bloomShader.setBloomIntensity(0.15f).setSampleCount(4);
        }
        if (bloom && bloomBuffer == null) {
            bloomBuffer = new FrameBuffer(viewWidth, viewHeight, GL_TEXTURE_2D);
            bloomBuffer.resize(viewWidth, viewHeight);
        } else if (!bloom && bloomBuffer != null) {
            bloomBuffer.destroy();
            bloomBuffer = null;
        }

        if (bloom && !antialiasing) {
            bloomBuffer.bindForWriting();
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        _draw();

        if (antialiasing) {
            msaaBuffer.unbind();
            if (bloom) {
                bloomBuffer.bindForWriting();
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                bloomBuffer.unbind();

                msaaBuffer.blit(bloomBuffer.fbo()); // FIXME: this is a nasty, horrid hack
            } else {
                msaaBuffer.blit();
            }
        }

        if (bloom) {
            if (!antialiasing) {
                bloomBuffer.unbind();
            }

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            ImmediateProjection.toOrthographicProjection(0, 0, viewWidth, viewHeight);

            glActiveTexture(GL_TEXTURE0);
            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, bloomBuffer.id());
            bloomShader.attach();

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

            bloomShader.detach();
            ImmediateProjection.toFrustrumProjection();
            glPopAttrib();
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
