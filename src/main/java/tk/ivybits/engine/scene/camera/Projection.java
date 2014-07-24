package tk.ivybits.engine.scene.camera;

import static java.lang.Math.*;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

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

    public Projection setProjection(float fieldOfView, float aspectRatio, float zNear, float zFar) {
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
        return this;
    }

    public Projection resetModelMatrix() {
        modelMatrix = new Matrix4f();
        return this;
    }

    public Projection resetViewMatrix() {
        viewMatrix = new Matrix4f();
        return this;
    }

    public Projection scale(float x, float y, float z) {
        Matrix4f.scale(new Vector3f(x, y, z), modelMatrix, modelMatrix);
        return this;
    }

    public Projection translate(float x, float y, float z) {
        Matrix4f.translate(new Vector3f(x, y, z), modelMatrix, modelMatrix);
        return this;
    }

    public Projection translateCamera(float x, float y, float z) {
        Matrix4f.translate(new Vector3f(-x, -y, -z), viewMatrix, viewMatrix);
        return this;
    }


    public Projection rotateCamera(float pitch, float yaw, float roll) {
        // roll pitch yaw
        Matrix4f.rotate((float) toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(roll), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
//        float a = (float) toRadians(yaw);
//        float b = (float) toRadians(pitch);
//        float y = (float) toRadians(roll);
//        viewMatrix.loadTranspose(FloatBuffer.wrap(new float[]{
//                (float) (cos(a) * cos(b)), (float) (cos(a) * sin(b) * sin(y) - sin(a) * cos(y)), (float) (cos(a) * sin(b) * cos(y) + sin(a) * sin(y)), 0,
//                (float) (cos(a) * cos(b)), (float) (cos(a) * sin(b) * sin(y) + sin(a) * cos(y)), (float) (cos(a) * sin(b) * cos(y) - sin(a) * sin(y)), 0,
//                (float) -sin(b), (float) (cos(b) * sin(y)), (float) (cos(b) * sin(y)), 0,
//                0, 0, 0, 1
//        }));
        return this;
    }

    public Projection rotate(float pitch, float yaw, float roll) {
        Matrix4f.rotate((float) toRadians(pitch), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
        Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
        Matrix4f.rotate((float) toRadians(roll), new Vector3f(1, 0, 0), modelMatrix, modelMatrix);
        return this;
    }

    public Projection setModelMatrix(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
        return this;
    }
}
