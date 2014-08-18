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
import tk.ivybits.engine.util.ToString;

import java.awt.*;

import static tk.ivybits.engine.util.ToString.Printable;

public class Material {
    public @Printable IResource ambientMap;
    public @Printable IResource diffuseMap;
    public @Printable IResource glossMap;
    public @Printable IResource alphaTexture;
    public @Printable IResource normalMap;
    public @Printable IResource heightMap;
    public @Printable Color ambientColor = Color.BLACK;
    public @Printable Color diffuseColor = Color.WHITE;
    public @Printable Color specularColor = Color.WHITE;
    public @Printable float shininess;
    public @Printable float opaqueness = 1;
    public @Printable float reflectivity = 0;
    public @Printable float refractionIndex = 1;
    public @Printable String name;

    public Material(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
