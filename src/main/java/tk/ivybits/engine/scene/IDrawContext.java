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

import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.Material;

public interface IDrawContext {
    public static enum Capability {
        NORMAL_MAPS, SPECULAR_MAPS, OBJECT_SHADOWS, ALPHA_TESTING, FOG, ANTIALIASING, BLOOM
    }

    void useMaterial(Material material);

    ITesselator createTesselator(int flags, int type);

    <T> boolean isSupported(Capability cap);

    void setEnabled(Capability cap, boolean flag);

    boolean isEnabled(Capability cap);
}
