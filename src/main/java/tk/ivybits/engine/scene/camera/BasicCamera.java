/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.scene.camera;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.util.ToString;

import java.util.Stack;

import static java.lang.Math.*;
import static java.lang.Math.toRadians;
import static tk.ivybits.engine.util.ToString.*;

/**
 * A simple camera implementing {@link tk.ivybits.engine.scene.camera.ICamera}
 */
public class BasicCamera implements ICamera {
    protected @Printable float x, y, z;
    protected @Printable float pitch, yaw, roll;
    protected IScene scene;
    protected Stack<Matrix4f> viewMatrixStack = new Stack<>();
    protected Matrix4f projectionMatrix = new Matrix4f();
    protected @Printable float fieldOfView, aspectRatio, zNear, zFar;

    /**
     * Creates a new BasicCamera instance.
     *
     * @param scene The {@link tk.ivybits.engine.scene.IScene} instance to forward transformations to.
     */
    public BasicCamera(IScene scene) {
        this.scene = scene;
        viewMatrixStack.push(new Matrix4f());
    }

    protected void updateView() {
        Matrix4f viewMatrix = viewMatrixStack.peek();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) toRadians(pitch), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) toRadians(roll), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.translate(new Vector3f(-x, -y, -z), viewMatrix, viewMatrix);
        scene.setViewTransform(viewMatrix);
    }

    protected void updateProjection() {
        projectionMatrix.setIdentity();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera setRotation(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        updateView();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera setAspectRatio(float ratio) {
        this.aspectRatio = ratio;
        updateProjection();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        updateView();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera setFieldOfView(float fov) {
        this.fieldOfView = fov;
        updateProjection();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera setClip(float zFar, float zNear) {
        this.zNear = zNear;
        this.zFar = zFar;
        updateProjection();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera pushMatrix() {
        viewMatrixStack.push(new Matrix4f());
        scene.setViewTransform(viewMatrixStack.peek());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BasicCamera popMatrix() {
        viewMatrixStack.pop();
        scene.setViewTransform(viewMatrixStack.peek());
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matrix4f getViewTransform() {
        return viewMatrixStack.peek();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matrix4f getProjectionTransform() {
        return projectionMatrix;
    }

    /*
        FIXME: the following methods do not respect the behaviour specified in ICamera, since they return
        FIXME: the last set values rather than the values of the current view/projection matrix.
    */

    /**
     * {@inheritDoc}
     */
    @Override
    public float x() {
        return x;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float y() {
        return y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float z() {
        return z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float pitch() {
        return pitch;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float yaw() {
        return yaw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float roll() {
        return roll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float dx() {
        return (float) (cos(toRadians(yaw - 90)) * m());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float dy() {
        return (float) -sin(toRadians(pitch));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float dz() {
        return (float) sin(toRadians(yaw - 90)) * m();
    }

    private float m() {
        return (float) cos(toRadians(pitch));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getFieldOfView() {
        return fieldOfView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getAspectRatio() {
        return aspectRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getZNear() {
        return zNear;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getZFar() {
        return zFar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
