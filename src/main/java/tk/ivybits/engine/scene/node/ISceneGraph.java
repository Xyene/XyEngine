package tk.ivybits.engine.scene.node;

public interface ISceneGraph {
    ISceneNode getRoot();

    IAtmosphere getAtmosphere();

    void addSceneChangeListener(ISceneChangeListener listener);

    void addSceneChangeListener(ISceneChangeListener listener, boolean fireCreateEvents);

    void removeSceneChangeListener(ISceneChangeListener listener);
}
