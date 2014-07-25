package tk.ivybits.engine.scene.camera;

import org.lwjgl.util.vector.Vector3f;

public interface ICamera {
    ICamera setRotation(float pitch, float yaw, float roll);

    float x();

    float y();

    float z();

    float pitch();

    float yaw();

    float roll();

    ICamera setAspectRatio(float v);

    ICamera setPosition(float x, float y, float z);

    ICamera setFieldOfView(float fov);

    ICamera setClip(float zFar, float zNear);

    ICamera pushMatrix();

    ICamera popMatrix();

    float dx();

    float dy();

    float dz();

    float getFieldOfView();

    float getAspectRatio();

    float getZNear();

    float getZFar();

    Vector3f position();

    Vector3f direction();
}
