package tk.ivybits.engine.scene.camera;

import tk.ivybits.engine.scene.IScene;

import static java.lang.Math.*;

public class FPSCamera extends SimpleCamera {

    public FPSCamera(IScene scene) {
        super(scene);
    }

    public void walkBackwards(float distance) {
        x -= distance * (float) sin(toRadians(yaw));
        z += distance * (float) cos(toRadians(yaw));
        updateView();
    }

    public void walkForward(float distance) {
        x += distance * (float) sin(toRadians(yaw));
        z -= distance * (float) cos(toRadians(yaw));
        updateView();
    }

    public void strafeRight(float distance) {
        x -= distance * (float) sin(toRadians(yaw - 90));
        z += distance * (float) cos(toRadians(yaw - 90));
        updateView();
    }

    public void strafeLeft(float distance) {
        x -= distance * (float) sin(toRadians(yaw + 90));
        z += distance * (float) cos(toRadians(yaw + 90));
        updateView();
    }

    @Override
    public FPSCamera setRotation(float pitch, float yaw, float roll) {
        return (FPSCamera) super.setRotation(pitch, yaw, roll);
    }

    @Override
    public FPSCamera setAspectRatio(float aspectRatio) {
        return (FPSCamera) super.setAspectRatio(aspectRatio);
    }

    @Override
    public FPSCamera setPosition(float x, float y, float z) {
        return (FPSCamera) super.setPosition(x, y, z);
    }

    @Override
    public FPSCamera setFieldOfView(float fov) {
        return (FPSCamera) super.setFieldOfView(fov);
    }

    @Override
    public FPSCamera setClip(float zFar, float zNear) {
        return (FPSCamera) super.setClip(zFar, zNear);
    }

    @Override
    public FPSCamera pushMatrix() {
        return (FPSCamera) super.pushMatrix();
    }

    @Override
    public FPSCamera popMatrix() {
        return (FPSCamera) super.popMatrix();
    }

    @Override
    public String toString() {
        return "FPSCamera{" +
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