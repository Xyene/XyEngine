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

package tk.ivybits.engine.scene.node;

import java.awt.*;

public interface IPointLight {
    IPointLight setPosition(float x, float y, float z);

    float x();

    float y();

    float z();

    Color getAmbientColor();

    IPointLight setAmbientColor(Color ambientColor);

    Color getDiffuseColor();

    IPointLight setDiffuseColor(Color diffuseColor);

    Color getSpecularColor();

    IPointLight setSpecularColor(Color specularColor);

    float getIntensity();

    IPointLight setIntensity(float intensity);

    float getAttenuation();

    IPointLight setAttenuation(float attenuation);
}
