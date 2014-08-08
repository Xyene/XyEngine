package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.scene.PriorityComparableDrawable;
import tk.ivybits.engine.gl.scene.gl20.aa.MSAAFBO;
import tk.ivybits.engine.gl.scene.gl20.bloom.BloomEffect;
import tk.ivybits.engine.gl.scene.gl20.shader.BaseShader;
import tk.ivybits.engine.gl.scene.gl20.shader.PhongLightingShader;
import tk.ivybits.engine.gl.scene.gl20.shader.RawRenderShader;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.gl.texture.RenderBuffer;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.Frustum;
import tk.ivybits.engine.scene.camera.BasicCamera;
import tk.ivybits.engine.scene.event.SceneChangeAdapter;
import tk.ivybits.engine.scene.node.*;

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
    private HashMap<FrameBuffer, Matrix4f> shadowMapBuffers = new HashMap<>();
    /**
     * Reference for GL20Tesselator - vertex attribute offsets
     */
    BaseShader currentGeometryShader;
    private ISceneGraph sceneGraph;
    private MSAAFBO msaaBuffer;
    private Frustum frustum = new Frustum();

    private Matrix4f viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();
    public int drawn = 0;

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
        for (FrameBuffer fbo : shadowMapBuffers.keySet()) fbo.resize(width, height);
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
                FrameBuffer self;
                shadowMapBuffers.put(
                        self = new FrameBuffer(viewWidth, viewHeight)
                                .attach(RenderBuffer.newDepthBuffer(viewWidth, viewHeight))
                                .attach(new Texture(GL_TEXTURE_2D, GL_DEPTH_COMPONENT, viewWidth, viewHeight)),
                        new Matrix4f()
                );
            }
        } else if (shadowMapBuffers.size() > numLights) {
            Iterator<Map.Entry<FrameBuffer, Matrix4f>> iterator = shadowMapBuffers.entrySet().iterator();
            for (int n = 0; n < shadowMapBuffers.size(); n++) {
                iterator.next();
                iterator.remove();
            }
        }

        Iterator<FrameBuffer> maps = shadowMapBuffers.keySet().iterator();
        for (int n = 0; n < numLights; n++) {
            camera.pushMatrix();
            ISpotLight light = spotLights.get(n);

            FrameBuffer fbo = maps.next();
            fbo.bind();

            glClear(GL_DEPTH_BUFFER_BIT); // Clear only depth buffer
            glLoadIdentity();

            rawGeometryShader.setProjectionTransform(projectionMatrix);

            rawGeometryShader.getProgram().attach();

            camera.setRotation(light.pitch(), light.yaw(), 0);
            camera.setPosition(light.x(), light.y(), light.z());

            shadowMapBuffers.put(fbo, viewMatrix);
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
        for (PriorityComparableDrawable entity : tracker) {
            entity.actor.update(1); // TODO: real delta
            if (frustum.bbInFrustum(entity.actor.x(), entity.actor.y(), entity.actor.z(), entity.actor.getBoundingBox())) {
                visible.add(entity);
            }
        }

        if (drawContext.isEnabled(ALPHA_TESTING)) {
            glEnable(GL_CULL_FACE);
            glCullFace(GL_FRONT);
            for (PriorityComparableDrawable entity : visible) {
                if (!entity.drawable.isTransparent()) continue;
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                entity.drawable.draw(this);
            }
            glCullFace(GL_BACK);
            for (PriorityComparableDrawable entity : visible) {
                if (!entity.drawable.isTransparent()) continue;
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                entity.drawable.draw(this);
                drawn++;
            }
            glDisable(GL_CULL_FACE);
            for (PriorityComparableDrawable entity : visible) {
                if (entity.drawable.isTransparent()) continue;
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                entity.drawable.draw(this);
            }
        } else {
            for (PriorityComparableDrawable entity : visible) {
                currentGeometryShader.setModelTransform(entity.actor.getTransform());
                entity.drawable.draw(this);
            }
        }

        drawn = visible.size();

        if (lightingShader != null) lightingShader.getProgram().detach();
    }

    @Override
    public void draw() {
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        if (drawContext.isEnabled(OBJECT_SHADOWS)) {
            glPushAttrib(GL_ALL_ATTRIB_BITS);
            generateShadowMaps();
            glPopAttrib();
        } else if (shadowMapBuffers.size() > 0) {
            Iterator<Map.Entry<FrameBuffer, Matrix4f>> iterator = shadowMapBuffers.entrySet().iterator();
            for (int n = 0; n < shadowMapBuffers.size(); n++) {
                iterator.next();
                iterator.remove();
            }
        }

        boolean antialiasing = drawContext.isEnabled(ANTIALIASING);

        if (antialiasing && msaaBuffer == null) {
            msaaBuffer = new MSAAFBO(viewWidth, viewHeight, 4);
            glEnable(GL_MULTISAMPLE);
            glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        } else if (!antialiasing && msaaBuffer != null) {
            msaaBuffer.destroy();
            msaaBuffer = null;
            glDisable(GL_MULTISAMPLE);
            glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE);
        }
        if (antialiasing) {
            msaaBuffer.bindFramebuffer();
        }

        boolean bloom = drawContext.isEnabled(BLOOM);
        if (bloom && bloomEffect == null) {
            bloomEffect = new BloomEffect(viewWidth, viewHeight, 2, 0.6f);
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

            bloomEffect.getOutputBuffer().bind();

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
            ImmediateProjection.toFrustrumProjection();

            bloomEffect.getOutputBuffer().unbind();

            glEnable(GL_DEPTH_TEST);
        }

        glPopAttrib();
    }

    @Override
    public void setViewTransform(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
        currentGeometryShader.setViewTransform(viewMatrix);
        frustum.setTransform(projectionMatrix, viewMatrix);
    }

    @Override
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    @Override
    public void setProjectionTransform(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        currentGeometryShader.setProjectionTransform(projectionMatrix);
        frustum.setTransform(projectionMatrix, viewMatrix);
    }

    @Override
    public Matrix4f getProjectionTransform() {
        return projectionMatrix;
    }
}
