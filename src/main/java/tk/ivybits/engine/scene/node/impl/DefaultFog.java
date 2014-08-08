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

import tk.ivybits.engine.scene.node.IFog;

import java.awt.*;

public class DefaultFog implements IFog {
    private DefaultSceneGraph scene;
    private Color fogColor = Color.LIGHT_GRAY;
    private float fogNear = 5, fogFar = 50;

    public DefaultFog(DefaultSceneGraph scene) {
        this.scene = scene;
    }

    public Color getFogColor() {
        return fogColor;
    }

    public DefaultFog setFogColor(Color fogColor) {
        this.fogColor = fogColor;
        scene.fireEvent("fogUpdated", this);
        return this;
    }

    public float getFogNear() {
        return fogNear;
    }

    public DefaultFog setFogNear(float fogNear) {
        this.fogNear = fogNear;
        scene.fireEvent("fogUpdated", this);
        return this;
    }

    public float getFogFar() {
        return fogFar;
    }

    public DefaultFog setFogFar(float fogFar) {
        this.fogFar = fogFar;
        scene.fireEvent("fogUpdated", this);
        return this;
    }
}
