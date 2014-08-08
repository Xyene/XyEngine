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

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private final Material material;
    private final List<Face> faces;

    public Mesh(Material material) {
        this(material, new ArrayList<Face>());
    }

    public Mesh(Material material, List<Face> faces) {
        this.material = material;
        this.faces = faces;
    }

    // TODO: though this should exit extremely quickly, perhaps cache result?
    public boolean hasNormals() {
        for (Face face : faces)
            for (Vertex vertex : face)
                if (vertex.normal != null)
                    return true;
        return false;
    }

    public boolean hasUVs() {
        for (Face face : faces)
            for (Vertex vertex : face)
                if (vertex.uv != null)
                    return true;
        return false;
    }

    public boolean hasTangents() {
        for (Face face : faces)
            for (Vertex vertex : face)
                if (vertex.tangent != null)
                    return true;
        return false;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public Material getMaterial() {
        return material;
    }
}
