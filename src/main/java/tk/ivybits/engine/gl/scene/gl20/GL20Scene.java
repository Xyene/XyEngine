package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.gl.Projection;
import tk.ivybits.engine.gl.shader.IGeometryShader;
import tk.ivybits.engine.gl.shader.IShader;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.*;
import tk.ivybits.engine.scene.camera.EulerCamera;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.light.AtmosphereModel;
import tk.ivybits.engine.scene.model.GeometryDrawable;
import tk.ivybits.engine.scene.model.IGeometry;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class GL20Scene implements IScene {
    private IDrawContext drawContext = new GL20DrawContext(this);
    private LinkedHashMap<IActor, IDrawable> tracker = new LinkedHashMap<>();
    private ICamera camera = new EulerCamera();
    private IGeometryShader defaultShader;
    private IShader postRenderShader;
    private FrameBuffer fbo;
    private AtmosphereModel atmosphere = new AtmosphereModel();
    private int viewWidth, viewHeight;

    public GL20Scene() {
        if (GLContext.getCapabilities().GL_EXT_framebuffer_object)
            fbo = new FrameBuffer(Display.getWidth(), Display.getHeight(), GL_TEXTURE_2D);
    }

    @Override
    public IActor createActor(IGeometry model) {
        DefaultActor actor = new DefaultActor(model);
        tracker.put(actor, new GeometryDrawable(drawContext, model));
        return actor;
    }

    @Override
    public void destroyActor(IActor actor) {
        IDrawable drawable = tracker.remove(actor);
        if (drawable != null) drawable.destroy();
    }

    @Override
    public void revalidateActor(IActor actor) {
        destroyActor(actor);
        tracker.put(actor, new GeometryDrawable(drawContext, actor.getGeometry()));
    }

    @Override
    public AtmosphereModel getAtmosphere() {
        return atmosphere;
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
    public void setDefaultShader(IGeometryShader shader) {
        this.defaultShader = shader;
    }

    @Override
    public void setRenderedShader(IShader shader) {
        this.postRenderShader = shader;
    }

    @Override
    public void setViewportSize(int width, int height) {
        if (fbo != null) fbo.resize(width, height);
        this.viewWidth = width;
        this.viewHeight = height;
    }

    private void _draw() {
        glPushAttrib(GL_TRANSFORM_BIT);
        glMatrixMode(GL_MODELVIEW);
        glRotatef(camera.pitch(), 1, 0, 0);
        glRotatef(camera.yaw(), 0, 1, 0);
        glRotatef(camera.roll(), 0, 0, 1);
        glTranslatef(-camera.x(), -camera.y(), -camera.z());
        glPopAttrib();

        if (defaultShader != null) defaultShader.attach();

        for (Map.Entry<IActor, IDrawable> entity : tracker.entrySet()) {
            IActor actor = entity.getKey();
            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glPushMatrix();
            glRotatef(actor.pitch(), 1, 0, 0);
            glRotatef(actor.yaw(), 0, 1, 0);
            glRotatef(actor.roll(), 0, 0, 1);
            glTranslatef(actor.x(), actor.y(), actor.z());
            entity.getValue().draw();
            glPopMatrix();
            glPopAttrib();
        }
        if (defaultShader != null) defaultShader.detach();
    }

    @Override
    public void draw() {
        if (fbo != null && postRenderShader != null) {
            fbo.bind();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            _draw();
            fbo.unbind();

            glPushAttrib(GL_ALL_ATTRIB_BITS);
            glDisable(GL_LIGHTING);
            glDisable(GL_DEPTH_TEST);
            Projection.toOrthographicProjection(0, 0, viewWidth, viewHeight);

            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, fbo.getTexture());
            postRenderShader.attach();

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

            postRenderShader.detach();
            Projection.toFrustrumProjection();
            glPopAttrib();
        } else {
            _draw();
        }
    }

    @Override
    public IShader getRenderedShader() {
        return postRenderShader;
    }

    @Override
    public IGeometryShader getDefaultShader() {
        return defaultShader;
    }
}
