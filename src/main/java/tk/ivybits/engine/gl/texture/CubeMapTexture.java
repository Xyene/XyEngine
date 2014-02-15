package tk.ivybits.engine.gl.texture;

import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.texture.ITexture;

import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;

public class CubeMapTexture implements ITexture {
    public void update(IScene scene) {

    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, id());
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }
}
