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

package tk.ivybits.engine.scene;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.scene.geometry.BoundingBox;

public interface IActor {
    void update(float delta);

    void position(float x, float y, float z);

    float x();

    float y();

    float z();

    float pitch();

    float yaw();

    float roll();

    BoundingBox getBoundingBox();

    Matrix4f getTransform();

    IDrawable createDrawable(IDrawContext context);

    float sx();

    float sy();

    float sz();

    void scale(float sx, float sy, float sz);

    void rotate(float pitch, float yaw, float roll);
}
