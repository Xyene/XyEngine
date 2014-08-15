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

package sandbox;

import tk.ivybits.engine.io.model.ModelIO;
import tk.ivybits.engine.io.res.IResourceFinder;
import tk.ivybits.engine.scene.model.Model;
import tk.ivybits.engine.scene.node.impl.GeometryActor;

import java.io.IOException;

public class MagicCircleActor extends GeometryActor {
    private float size = 2;
    private int mod = 1;

    public MagicCircleActor() throws IOException {
        super(ModelIO.read("/tk/ivybits/engine/game/model/magic-circle-1.obj", IResourceFinder.RESOURCE_FINDER_RESOURCE));
        priority = 0;
        scale(2, 2, 2);
    }

    @Override
    public void update(float delta) {
        size += delta / 5000 * mod;
        if (size > 2 || size < 1) mod *= -1;
        // scale(size, size, size);
        // Prevent locking > 2 and < 1
        size = Math.min(size, 2);
        size = Math.max(size, 1);
        rotate(pitch, yaw + delta / 50, roll);
    }
}
