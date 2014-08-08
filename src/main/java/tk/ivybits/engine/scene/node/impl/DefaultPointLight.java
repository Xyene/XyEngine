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

import tk.ivybits.engine.scene.node.IPointLight;

import java.awt.*;

public class DefaultPointLight implements IPointLight {
    private final DefaultSceneGraph scene;
    private float x, y, z;
    private Color ambientColor = Color.BLACK;
    private Color diffuseColor = Color.WHITE;
    private Color specularColor = Color.WHITE;
    private float intensity = 1;

    public DefaultPointLight(DefaultSceneGraph scene) {
        this.scene = scene;
    }

    public DefaultPointLight setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        scene.fireEvent("pointLightUpdated", this);
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

    public DefaultPointLight setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
        scene.fireEvent("pointLightUpdated", this);
        return this;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public DefaultPointLight setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
        scene.fireEvent("pointLightUpdated", this);
        return this;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public DefaultPointLight setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
        scene.fireEvent("pointLightUpdated", this);
        return this;
    }

    public float getIntensity() {
        return intensity;
    }

    public DefaultPointLight setIntensity(float intensity) {
        this.intensity = intensity;
        scene.fireEvent("pointLightUpdated", this);
        return this;
    }
}
