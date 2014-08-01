package tk.ivybits.engine.scene.node;

import tk.ivybits.engine.scene.event.ISceneChangeListener;

public interface ISceneGraph {
    ISceneNode getRoot();

    IAtmosphere getAtmosphere();

    void addSceneChangeListener(ISceneChangeListener listener);

    void addSceneChangeListener(ISceneChangeListener listener, boolean fireCreateEvents);

    void removeSceneChangeListener(ISceneChangeListener listener);
}
