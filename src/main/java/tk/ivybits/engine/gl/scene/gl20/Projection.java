package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Projection {
    protected Matrix4f viewMatrix = new Matrix4f();
    protected Matrix4f projectionMatrix = new Matrix4f();
    protected Vector3f eyePos = new Vector3f();

    public Matrix4f getViewTransform() {
        return viewMatrix;
    }

    public void setViewTransform(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getProjectionTransform() {
        return projectionMatrix;
    }

    public void setProjectionTransform(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public Vector3f getEyePosition() {
        return eyePos;
    }

    public void setEyePosition(Vector3f eyePos) {
        this.eyePos = eyePos;
    }
}
