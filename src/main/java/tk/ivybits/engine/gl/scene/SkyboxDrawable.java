package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.geometry.Sphere;

import static tk.ivybits.engine.gl.GL.*;

class SkyboxDrawable implements IDrawable {
    private final Skybox skybox;
    private SkyboxShader shader;
    private IDrawable sphere;

    public SkyboxDrawable(Skybox skybox) {
        this.skybox = skybox;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void draw(IScene scene) {
        if (shader == null) {
            shader = new SkyboxShader(scene);
            sphere = Sphere.createSphere(scene.getDrawContext(), 100, 4, false, false);
        }

        glPushAttrib(GL_ALL_ATTRIB_BITS);
        shader.getProgram().attach();
        shader.setProjectionTransform(scene.getProjection().getProjectionTransform());
        shader.setViewTransform(scene.getProjection().getViewTransform());
        shader.setModelTransform(skybox.getTransform());
        shader.getProgram().setUniform("u_skybox", 0);
        skybox.getEnvironmentMap().bind(0);
        sphere.draw(scene);
        shader.getProgram().detach();
        glPopAttrib();
    }

    @Override
    public void destroy() {
        // TODO
    }

    @Override
    public int priority() {
        return Integer.MAX_VALUE;
    }
}
