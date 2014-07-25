package tk.ivybits.engine.scene.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.scene.IScene;

import java.util.Stack;

import static java.lang.Math.*;
import static java.lang.Math.toRadians;

public class SimpleCamera implements ICamera {
    protected float x = 0;
    protected float y = 0;
    protected float z = 0;
    protected float pitch = 0;
    protected float yaw = 0;
    protected float roll = 0;
    protected IScene scene;
    protected Stack<Matrix4f> viewMatrixStack = new Stack<>();
    protected Matrix4f viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();
    protected float fieldOfView, aspectRatio, zNear, zFar;

    public SimpleCamera(IScene scene) {
        this.scene = scene;
        viewMatrixStack.push(viewMatrix);
    }

    protected void updateView() {
        viewMatrix = new Matrix4f();
        Matrix4f.rotate((float) toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(roll), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.translate(new Vector3f(-x, -y, -z), viewMatrix, viewMatrix);
        scene.setViewTransform(viewMatrix);
    }

    protected void updateProjection() {
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
        scene.setProjectionTransform(projectionMatrix);
    }

    @Override
    public SimpleCamera setRotation(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        updateView();
        return this;
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float z() {
        return z;
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @Override
    public float yaw() {
        return yaw;
    }

    @Override
    public float roll() {
        return roll;
    }

    @Override
    public SimpleCamera setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        updateProjection();
        return this;
    }

    @Override
    public SimpleCamera setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        updateView();
        return this;
    }

    @Override
    public SimpleCamera setFieldOfView(float fov) {
        this.fieldOfView = fov;
        updateProjection();
        return this;
    }

    @Override
    public SimpleCamera setClip(float zFar, float zNear) {
        this.zNear = zNear;
        this.zFar = zFar;
        updateProjection();
        return this;
    }

    @Override
    public SimpleCamera pushMatrix() {
        viewMatrix = new Matrix4f();
        viewMatrixStack.push(viewMatrix);
        scene.setViewTransform(viewMatrix);
        return this;
    }

    @Override
    public SimpleCamera popMatrix() {
        viewMatrixStack.pop();
        viewMatrix = viewMatrixStack.peek();
        scene.setViewTransform(viewMatrix);
        return this;
    }

    @Override
    public float dx() {
        return (float) (cos(toRadians(yaw - 90)) * m());
    }

    @Override
    public float dy() {
        return (float) -sin(toRadians(pitch));
    }

    @Override
    public float dz() {
        return (float) sin(toRadians(yaw - 90)) * m();
    }

    private float m() {
        return (float) cos(toRadians(pitch));
    }

    @Override
    public float getFieldOfView() {
        return fieldOfView;
    }

    @Override
    public float getAspectRatio() {
        return aspectRatio;
    }

    @Override
    public float getZNear() {
        return zNear;
    }

    @Override
    public float getZFar() {
        return zFar;
    }

    @Override
    public Vector3f position() {
        return new Vector3f(x(), y(), z());
    }

    @Override
    public Vector3f direction() {
        return new Vector3f(dx(), dy(), dz());
    }

    @Override
    public boolean isSphereInFrustum(Vector3f position, float v) {
        return true;
    }

    @Override
    public boolean isOccluded(Vector3f position) {
        return false;
    }

    @Override
    public String toString() {
        return "SimpleCamera{" +
                "roll=" + roll +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", pitch=" + pitch +
                ", yaw=" + yaw +
                ", fieldOfView=" + fieldOfView +
                ", aspectRatio=" + aspectRatio +
                ", zNear=" + zNear +
                ", zFar=" + zFar +
                '}';
    }
}
