package tk.ivybits.engine.scene.camera;

import static java.lang.Math.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class Projection {
    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    protected Matrix4f projectionMatrix = new Matrix4f(), modelMatrix = new Matrix4f(), viewMatrix = new Matrix4f();

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setProjection(float fieldOfView, float aspectRatio, float zNear, float zFar) {
        projectionMatrix = new Matrix4f();
        float scaleY = (float) (1 / tan(toRadians(fieldOfView / 2f)));
        float scaleX = scaleY / aspectRatio;
        float frustrumLength = zFar - zNear;

        projectionMatrix.m00 = scaleX;
        projectionMatrix.m11 = scaleY;
        projectionMatrix.m22 = -((zFar + zNear) / frustrumLength);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * zNear * zFar) / frustrumLength);
        projectionMatrix.m33 = 0;
    }

    public void resetModelMatrix() {
        modelMatrix = new Matrix4f();
    }

    public void resetViewMatrix() {
        viewMatrix = new Matrix4f();
    }

    public void scale(float x, float y, float z) {
        Matrix4f.scale(new Vector3f(x, y, z), modelMatrix, modelMatrix);
    }

    public void translate(float x, float y, float z) {
        Matrix4f.translate(new Vector3f(x, y, z), modelMatrix, modelMatrix);
    }

    public void translateCamera(float x, float y, float z) {
        Matrix4f.translate(new Vector3f(x, y, z), viewMatrix, viewMatrix);
    }

    public void rotateCamera(float pitch, float yaw, float roll) {
        // roll pitch yaw
        Matrix4f.rotate((float) toRadians(roll), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
    }

    public void rotate(float pitch, float yaw, float roll) {
        Matrix4f.rotate((float) toRadians(pitch), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
        Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
        Matrix4f.rotate((float) toRadians(roll), new Vector3f(1, 0, 0), modelMatrix, modelMatrix);
    }
}
