package tk.ivybits.engine.scene.texture;

public interface ITexture {
    void bind();

    void unbind();

    int id();

    int width();

    int height();
}
