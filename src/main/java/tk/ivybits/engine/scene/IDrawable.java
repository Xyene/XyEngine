package tk.ivybits.engine.scene;

public interface IDrawable {
    boolean isTransparent();

    void draw(IScene scene);

    void destroy();

    int priority();
}
