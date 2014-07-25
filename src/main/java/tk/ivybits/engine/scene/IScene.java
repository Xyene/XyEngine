package tk.ivybits.engine.scene;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.scene.camera.ICamera;
import tk.ivybits.engine.scene.node.ISceneGraph;

public interface IScene {
    ISceneGraph getSceneGraph();

    IDrawContext getDrawContext();

    void setViewportSize(int width, int height);

    void draw();

    void setViewTransform(Matrix4f viewMatrix);

    Matrix4f getViewMatrix();

    void setProjectionTransform(Matrix4f projectionMatrix);

    Matrix4f getProjectionTransform();
}
