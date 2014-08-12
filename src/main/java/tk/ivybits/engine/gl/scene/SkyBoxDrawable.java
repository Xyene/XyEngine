package tk.ivybits.engine.gl.scene;

import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.gl.scene.gl20.shader.BaseShader;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.geometry.Sphere;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static tk.ivybits.engine.gl.GL.*;

public class SkyBoxDrawable implements IDrawable {
    private final SkyBox skybox;

    private Texture[] textures;
    private int cubemapHandle;

    private SkyBoxShader shader;
    private IDrawable sphere;

    public Texture get(int target, BufferedImage image) {
        return new Texture(target, image)
                .setParameter(GL_TEXTURE_MAG_FILTER, GL_NEAREST)
                .setParameter(GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    }

    public SkyBoxDrawable(SkyBox skybox) {
        this.skybox = skybox;
    }

    @Override
    public boolean isTransparent() {
        return false;
    }

    @Override
    public void draw(IScene scene) {
        if (textures == null) {
            glEnable(GL_TEXTURE_CUBE_MAP);
            cubemapHandle = glGenTextures();
            glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapHandle);

            textures = new Texture[]{
                    get(GL_TEXTURE_CUBE_MAP_POSITIVE_X, skybox.xpos),
                    get(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, skybox.xneg),
                    get(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, skybox.ypos),
                    get(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, skybox.yneg),
                    get(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, skybox.zpos),
                    get(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, skybox.zneg)
            };

            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

            glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
            shader = new SkyBoxShader(scene);
            sphere = Sphere.createSphere(scene.getDrawContext(), 10000, 20, false, false);
        }

        glDepthMask(false);
        shader.getProgram().attach();
        shader.setProjectionTransform(scene.getProjectionTransform());
        shader.setViewTransform(scene.getViewMatrix());
        shader.setModelTransform(skybox.getTransform());
        shader.getProgram().setUniform("u_skybox", 0);
        glActiveTexture(GL_TEXTURE0);
       // glEnable(GL_TEXTURE_CUBE_MAP);
        glBindTexture(GL_TEXTURE_CUBE_MAP, cubemapHandle);
        //System.out.println("DRAW!");
        sphere.draw(scene);
        shader.getProgram().detach();
        glDepthMask(true);
    }

    @Override
    public void destroy() {
//        for (Texture tex : textures)
//            tex.destroy();
//        glDeleteTextures(cubemapHandle);
    }

    @Override
    public int priority() {
        return 0;
    }
}
