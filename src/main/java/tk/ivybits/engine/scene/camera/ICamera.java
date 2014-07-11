package tk.ivybits.engine.scene.camera;

import javax.vecmath.Vector3f;

public interface ICamera {
    void move(float dx, float dy, float dz);

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

    ICamera setZFar(float v);

    ICamera setZNear(float v);

    float dx();

    float dy();

    float dz();

    float getFieldOfView();

    float getAspectRatio();

    float getZNear();

    float getZFar();

    Vector3f position();

    Vector3f direction();

    boolean isSphereInFrustum(Vector3f position, float v);

    boolean isOccluded(Vector3f position);
}
