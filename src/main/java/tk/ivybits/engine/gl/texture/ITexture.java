package tk.ivybits.engine.gl.texture;

import java.awt.image.BufferedImage;

public interface ITexture {
    int width();

    int height();

    void bindTexture();

    void unbindTexture();

    int getTextureId();

    void destroy();
}
