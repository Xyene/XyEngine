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

import java.util.Arrays;
import java.util.Iterator;

public class Face implements Iterable<Vertex> {
    private final int vertexCount;
    private Vertex[] vertices;

    public Face(int vertexCount) {
        this.vertexCount = vertexCount;
        vertices = new Vertex[vertexCount];
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return Arrays.asList(vertices).iterator();
    }
}
