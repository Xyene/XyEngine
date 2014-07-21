package tk.ivybits.engine.gl.texture;

import tk.ivybits.engine.scene.texture.ITexture;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.glBindTexture;

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
    public void bind() {
        if (id == 0) {
            id = TextureLoader.getTexture(img, target, GL_LINEAR, true);
        }
        glBindTexture(target, id);
    }

    @Override
    public void unbind() {
        glBindTexture(target, 0);
    }

    @Override
    public int id() {
        return id;
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
