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

package tk.ivybits.engine.scene.geometry;

import tk.ivybits.engine.scene.IDrawable;

public interface ITesselator {
    int NORMAL_BUFFER = 0x1,
            UV_BUFFER = 0x2,
            TANGENT_BUFFER = 0x4;

    int getBufferFlags();

    void pushVertex(float x, float y, float z);

    void pushNormal(float x, float y, float z);

    void pushTexCoord(float u, float v);

    void pushTangent(float x, float y, float z);

    IDrawable createDrawable();
}
