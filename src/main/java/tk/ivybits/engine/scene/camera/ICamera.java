package tk.ivybits.engine.scene.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public interface ICamera {
    /**
     * Rotates the current view matrix.
     *
     * @param pitch The new pitch, in degrees.
     * @param yaw   The new yaw, in degrees.
     * @param roll  The new roll, in degrees.
     * @return This camera instance.
     */
    ICamera setRotation(float pitch, float yaw, float roll);

    /**
     * Returns the x coordinate of the current view matrix.
     *
     * @return The x coordinate.
     */
    float x();

    /**
     * Returns the y coordinate of the current view matrix.
     *
     * @return The y coordinate.
     */
    float y();

    /**
     * Returns the z coordinate of the current view matrix.
     *
     * @return The z coordinate.
     */
    float z();

    /**
     * Returns the pitch of the current view matrix.
     *
     * @return The pitch, in degrees.
     */
    float pitch();

    /**
     * Returns the yaw of the current view matrix.
     *
     * @return The yaw, in degrees.
     */
    float yaw();

    /**
     * Returns the roll of the current view matrix.
     *
     * @return The roll, in degrees.
     */
    float roll();

    /**
     * Sets the aspect ratio of the current projection matrix.
     *
     * @param ratio The new aspect ratio.
     * @return This camera instance.
     */
    ICamera setAspectRatio(float ratio);


    /**
     * Sets the position of the current view matrix.
     *
     * @param x The new x coordinate.
     * @param y The new y coordinate.
     * @param z The new z coordinate.
     * @return This camera instance.
     */
    ICamera setPosition(float x, float y, float z);

    /**
     * Sets the field of view of the current projection matrix.
     *
     * @param fov The new field of view.
     * @return This camera instance.
     */
    ICamera setFieldOfView(float fov);

    /**
     * Sets the near and far plane of this projection matrix's frustum.
     *
     * @param zFar  The distance to the far plane.
     * @param zNear The distance to the near plane.
     * @return This camera instance.
     */
    ICamera setClip(float zFar, float zNear);

    /**
     * Pushes a new view matrix onto the matrix stack.
     *
     * @return This camera instance.
     */
    ICamera pushMatrix();

    /**
     * Pops a view matrix off the matrix stack.
     *
     * @return This camera instance.
     */
    ICamera popMatrix();

    /**
     * Returns the view transform.
     *
     * @return The view transform.
     */
    Matrix4f getViewTransform();

    /**
     * Returns the projection transform.
     *
     * @return The projection transform.
     */
    Matrix4f getProjectionTransform();

    /**
     * Returns the x component of this camera's direction vector.
     *
     * @return The x-delta.
     */
    float dx();

    /**
     * Returns the y component of this camera's direction vector.
     *
     * @return The y-delta.
     */
    float dy();

    /**
     * Returns the z component of this camera's direction vector.
     *
     * @return The z-delta.
     */
    float dz();

    /**
     * Returns the field of view of the current projection matrix.
     *
     * @return The field of view.
     */
    float getFieldOfView();

    /**
     * Returns the aspect ratio of the current projection matrix.
     *
     * @return The aspect ratio.
     */
    float getAspectRatio();

    /**
     * Returns the distance to the near plane of the current projection matrix's frustum.
     *
     * @return The distance to the near plane.
     */
    float getZNear();

    /**
     * Returns the distance to the far plane of the current projection matrix's frustum.
     *
     * @return The distance to the far plane.
     */
    float getZFar();
}
