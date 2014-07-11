package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.gl.ImmediateProjection;
import tk.ivybits.engine.gl.scene.gl20.shadow.RawRenderShader;
import tk.ivybits.engine.gl.scene.gl20.shadow.ShadowMapFBO;
import tk.ivybits.engine.gl.scene.gl20.shadow.ShadowMapShader;
import tk.ivybits.engine.gl.shader.ISceneShader;
import tk.ivybits.engine.gl.shader.IShader;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.camera.SimpleCamera;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.node.*;
import tk.ivybits.engine.scene.node.impl.DefaultSceneGraph;

import static tk.ivybits.engine.gl.GL.*;

import java.util.*;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static tk.ivybits.engine.scene.IDrawContext.*;

public class GL20Scene implements IScene {
    GL20DrawContext drawContext = new GL20DrawContext(this);
    PriorityQueue<PriorityComparableDrawable> tracker = new PriorityQueue<>(1, new Comparator<PriorityComparableDrawable>() {
        @Override
        public int compare(PriorityComparableDrawable o1, PriorityComparableDrawable o2) {
            return o1.priority - o2.priority;
        }
    });
    private Projection proj = new Projection();
    private ICamera camera = new SimpleCamera(proj);
    PhongLightingShader lightingShader;
    private ISceneShader shadowMapShader;
    private BloomShader bloomShader;
    private FrameBuffer postRenderFBO;
    private int viewWidth;
    private int viewHeight;
    private RawRenderShader rawGeometryShader;
    private List<ShadowMapFBO> shadowMapFBOs = new ArrayList<>();
    /**
     * Reference for GL20Tesselator - vertex attribute offsets
     */
    public ISceneShader currentGeometryShader;
    private ISceneGraph sceneGraph;

    public GL20Scene(int viewWidth, int viewHeight, ISceneGraph sceneGraph) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.sceneGraph = sceneGraph;
        // TODO: only create if bloom is used
        if (GLContext.getCapabilities().GL_EXT_framebuffer_object) {
            postRenderFBO = new FrameBuffer(viewWidth, viewHeight, GL_TEXTURE_2D);
        }
        lightingShader = new PhongLightingShader(this, shadowMapFBOs);
        shadowMapShader = new ShadowMapShader();
        rawGeometryShader = new RawRenderShader();
        sceneGraph.addSceneChangeListener(new SceneChangeAdapter() {
            @Override
            public void actorAdded(IActor actor) {
                IDrawable draw = actor.createDrawable(drawContext);
                tracker.add(new PriorityComparableDrawable(actor, draw, draw.priority()));
            }

            @Override
            public void actorRemoved(IActor actor) {
                Iterator<PriorityComparableDrawable> i = tracker.iterator();
                while(i.hasNext()) {
                    PriorityComparableDrawable pcb = i.next();
                    if(pcb == actor) {
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
        if (postRenderFBO != null) postRenderFBO.resize(width, height);
        for (ShadowMapFBO fbo : shadowMapFBOs) fbo.resize(width, height);
        this.viewWidth = width;
        this.viewHeight = height;
    }

    public void drawGeometry() {
        for (PriorityComparableDrawable entity : tracker) {
            IActor actor = entity.wrapped;
            proj.resetModelMatrix();
            proj.translate(actor.x(), actor.y(), actor.z());
            proj.rotate(actor.pitch(), actor.yaw(), actor.roll());
            currentGeometryShader.setProjection(proj);
            entity.draw.draw(this);
        }
    }

    protected void _draw() {
        if (drawContext.isEnabled(DYNAMIC_SHADOWS)) {
            Projection camera = proj;

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
                //proj.setProjection(90, this.camera.getAspectRatio(), this.camera.getZFar(), this.camera.getZNear());

                proj.resetViewMatrix();
                proj.rotateCamera(light.pitch(), light.yaw(), 0);
                proj.translateCamera(-light.x(), -light.y(), -light.z());

                fbo.projection = proj.getViewMatrix();
                glEnable(GL_CULL_FACE);
                glCullFace(GL_FRONT); // Don't shadow self
                drawGeometry();
                glDisable(GL_CULL_FACE);
                rawGeometryShader.detach();

                fbo.unbind();
            }
            proj = camera;
        }

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();

        if (Keyboard.isKeyDown(Keyboard.KEY_I) && shadowMapFBOs.size() > 0) {
            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            ImmediateProjection.toOrthographicProjection(0, 0, viewWidth, viewHeight);

            glEnable(GL_TEXTURE_2D);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, shadowMapFBOs.get(shadowMapFBOs.size() - 1).id());
            shadowMapShader.attach();

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

            shadowMapShader.detach();
            ImmediateProjection.toFrustrumProjection();
            glPopAttrib();
        } else {
            currentGeometryShader = lightingShader;
            if (lightingShader != null) lightingShader.attach();

            if (drawContext.isEnabled(SORTED_ALPHA)) {
                glEnable(GL_CULL_FACE);
                glCullFace(GL_FRONT);
                drawGeometry();
                glCullFace(GL_BACK);
            }
            drawGeometry();
            if (drawContext.isEnabled(SORTED_ALPHA)) {
                glDisable(GL_CULL_FACE);
            }
            if (lightingShader != null) lightingShader.detach();
        }
    }

    @Override
    public void draw() {
        boolean bloom = false && drawContext.isEnabled(BLOOM) && postRenderFBO != null;
        if (bloom) {
            postRenderFBO.bind();
        }
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        _draw();

        if (bloom) {
            postRenderFBO.unbind();

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            ImmediateProjection.toOrthographicProjection(0, 0, viewWidth, viewHeight);

            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, postRenderFBO.id());
          // TODO  bloomShader.attach();

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

      // TODO      bloomShader.detach();
            ImmediateProjection.toFrustrumProjection();
            glPopAttrib();
        }
    }
}
