package tk.ivybits.engine.scene;

public interface IDrawable {
    void draw(IScene scene);

    void destroy();

    int priority();
}
