package tk.ivybits.engine.gl.texture;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class BufferedTexture implements ITexture {
    private final int target;
    private BufferedImage img;
    private int id;
    private int width, height;

    public BufferedTexture(int target, BufferedImage img) {
        this.target = target;
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    @Override
    public void bindTexture() {
        if (id == 0) {
            id = TextureLoader.getTexture(img, target, GL_LINEAR, true);
        }
        glBindTexture(target, id);
    }

    @Override
    public void unbindTexture() {
        glBindTexture(target, 0);
    }

    @Override
    public int getTextureId() {
        return id;
    }

    @Override
    public void destroy() {
        glDeleteTextures(id);
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }
}
