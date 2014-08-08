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

public class Model {
    private List<Mesh> meshes;

    public Model() {
        this(new ArrayList<Mesh>());
    }

    public Model(List<Mesh> faces) {
        this.meshes = faces;
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public void setMeshes(List<Mesh> faces) {
        this.meshes = faces;
    }
}
