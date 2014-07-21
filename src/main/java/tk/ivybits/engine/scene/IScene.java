package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.node.ISceneGraph;

public interface IScene {
    ISceneGraph getSceneGraph();

    IDrawContext getDrawContext();

    ICamera getCamera();

    void setViewportSize(int width, int height);

    void draw();
}
