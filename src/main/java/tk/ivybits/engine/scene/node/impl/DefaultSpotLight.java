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

package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.scene.node.ISpotLight;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class DefaultSpotLight implements ISpotLight {
    private final DefaultSceneGraph scene;
    private float x, y, z;
    private Color ambientColor = Color.BLACK;
    private Color diffuseColor = Color.WHITE;
    private Color specularColor = Color.WHITE;
    private float pitch;
    private float yaw;
    private float cutoff;
    private float intensity;

    public DefaultSpotLight(DefaultSceneGraph scene) {
        this.scene = scene;
    }

    public DefaultSpotLight setRotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }

    public float dx() {
        return (float) (cos(toRadians(yaw - 90)) * m());
    }

    public float dy() {
        return (float) -sin(toRadians(pitch));
    }

    public float dz() {
        return (float) sin(toRadians(yaw - 90)) * m();
    }

    private float m() {
        return (float) cos(toRadians(pitch));
    }

    public float getCutoff() {
        return cutoff;
    }

    public DefaultSpotLight setCutoff(float cutoff) {
        this.cutoff = cutoff;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }

    public DefaultSpotLight setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public Color getAmbientColor() {
        return ambientColor;
    }

    public DefaultSpotLight setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public DefaultSpotLight setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public DefaultSpotLight setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }

    public float getIntensity() {
        return intensity;
    }

    public DefaultSpotLight setIntensity(float intensity) {
        this.intensity = intensity;
        scene.fireEvent("spotLightUpdated", this);
        return this;
    }
}
