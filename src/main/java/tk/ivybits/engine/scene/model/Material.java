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

package tk.ivybits.engine.scene.model;

import tk.ivybits.engine.io.res.IResource;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Material {
    public IResource ambientTexture;
    public IResource diffuseTexture;
    public IResource specularTexture;
    public IResource alphaTexture;
    public IResource bumpMap;
    public Color ambientColor = Color.BLACK;
    public Color diffuseColor = Color.WHITE;
    public Color specularColor = Color.WHITE;
    public float shininess;
    public float transparency = 1;
    public String name;

    public Material(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Material{" +
                "ambientTexture=" + ambientTexture +
                ", diffuseTexture=" + diffuseTexture +
                ", specularTexture=" + specularTexture +
                ", alphaTexture=" + alphaTexture +
                ", ambientColor=" + ambientColor +
                ", diffuseColor=" + diffuseColor +
                ", specularColor=" + specularColor +
                ", shininess=" + shininess +
                ", transparency=" + transparency +
                ", name='" + name + '\'' +
                '}';
    }
}
