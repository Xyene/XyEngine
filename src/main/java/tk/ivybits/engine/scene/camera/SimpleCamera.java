package tk.ivybits.engine.scene.camera;

import javax.vecmath.Vector3f;

import static java.lang.Math.*;

public class SimpleCamera implements ICamera {
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float pitch = 0;
    private float yaw = 0;
    private float roll = 0;
    private Projection proj;
    private float fieldOfView, aspectRatio, zNear, zFar;

    public SimpleCamera(Projection proj) {
        this.proj = proj;
    }

    public void walkBackwards(float distance) {
        x -= distance * (float) Math.sin(Math.toRadians(yaw));
        z += distance * (float) Math.cos(Math.toRadians(yaw));
        updateView();
    }

    public void walkForward(float distance) {
        x += distance * (float) Math.sin(Math.toRadians(yaw));
        z -= distance * (float) Math.cos(Math.toRadians(yaw));
        updateView();
    }

    public void strafeRight(float distance) {
        x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
        updateView();
    }

    public void strafeLeft(float distance) {
        x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
        updateView();
    }

    @Override
    public void move(float dx, float dy, float dz) {
        throw new UnsupportedOperationException();
    }

    void updateView() {
        proj.resetViewMatrix();
        proj.rotateCamera(pitch, yaw, roll);
        proj.translateCamera(x, y, z);
    }

    @Override
    public ICamera setRotation(float pitch, float yaw, float roll) {
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
    public ICamera setAspectRatio(float aspectRatio) {
        proj.setProjection(fieldOfView, this.aspectRatio = aspectRatio, zNear, zFar);
        return this;
    }

    @Override
    public ICamera setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        updateView();
        return this;
    }

    @Override
    public ICamera setFieldOfView(float fov) {
        proj.setProjection(this.fieldOfView = fov, aspectRatio, zNear, zFar);
        return this;
    }

    @Override
    public ICamera setZFar(float zFar) {
        proj.setProjection(fieldOfView, aspectRatio, zNear, this.zFar = zFar);
        return this;
    }

    @Override
    public ICamera setZNear(float zNear) {
        proj.setProjection(fieldOfView, aspectRatio, this.zNear = zNear, zFar);
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