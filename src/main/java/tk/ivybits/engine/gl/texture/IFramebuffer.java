package tk.ivybits.engine.gl.texture;

public interface IFramebuffer {
    int width();

    int height();

    void bindFramebuffer();

    void unbindFramebuffer();

    int getFramebufferId();

    void resize(int width, int height);

    void blit();

    void blit(IFramebuffer other);

    void destroy();
}
