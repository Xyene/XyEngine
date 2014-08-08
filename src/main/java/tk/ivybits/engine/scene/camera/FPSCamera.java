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

import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.util.ToString;

import static java.lang.Math.*;

/**
 * An FPS-style implementation of {@link tk.ivybits.engine.scene.camera.ICamera}.
 * <p/>
 * Movement on the y axis is locked when strafing.
 */
public class FPSCamera extends BasicCamera {
    /**
     * Creates a new FPSCamera instance.
     *
     * @param scene The {@link tk.ivybits.engine.scene.IScene} instance to forward transformations to.
     */
    public FPSCamera(IScene scene) {
        super(scene);
    }

    /**
     * Moves this camera backwards.
     *
     * @param distance The distance to move.
     * @return This camera instance.
     */
    public FPSCamera walkBackwards(float distance) {
        x -= distance * (float) sin(toRadians(yaw));
        z += distance * (float) cos(toRadians(yaw));
        updateView();
        return this;
    }

    /**
     * Moves this camera forward.
     *
     * @param distance The distance to move.
     * @return This camera instance.
     */
    public FPSCamera walkForward(float distance) {
        x += distance * (float) sin(toRadians(yaw));
        z -= distance * (float) cos(toRadians(yaw));
        updateView();
        return this;
    }

    /**
     * Moves this camera to the right.
     *
     * @param distance The distance to move.
     * @return This camera instance.
     */
    public FPSCamera strafeRight(float distance) {
        x -= distance * (float) sin(toRadians(yaw - 90));
        z += distance * (float) cos(toRadians(yaw - 90));
        updateView();
        return this;
    }

    /**
     * Moves this camera to the left.
     *
     * @param distance The distance to move.
     * @return This camera instance.
     */
    public FPSCamera strafeLeft(float distance) {
        x -= distance * (float) sin(toRadians(yaw + 90));
        z += distance * (float) cos(toRadians(yaw + 90));
        updateView();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera setRotation(float pitch, float yaw, float roll) {
        return (FPSCamera) super.setRotation(pitch, yaw, roll);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera setAspectRatio(float ratio) {
        return (FPSCamera) super.setAspectRatio(ratio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera setPosition(float x, float y, float z) {
        return (FPSCamera) super.setPosition(x, y, z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera setFieldOfView(float fov) {
        return (FPSCamera) super.setFieldOfView(fov);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera setClip(float zFar, float zNear) {
        return (FPSCamera) super.setClip(zFar, zNear);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera pushMatrix() {
        return (FPSCamera) super.pushMatrix();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FPSCamera popMatrix() {
        return (FPSCamera) super.popMatrix();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToString.toString(this);
    }
}