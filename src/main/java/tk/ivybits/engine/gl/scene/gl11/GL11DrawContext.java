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

package tk.ivybits.engine.gl.scene.gl11;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.Material;

public class GL11DrawContext implements IDrawContext {
    final GL11Scene parent;
    final boolean[] caps = {
            false,   // Normal mapping
            false,   // Specular mapping
            false,   // Dynamic shadows
            true,    // Sorted alpha
            true,    // Fog
            false,    // ANTIALIASING todo: glSmoothHint etc
            false    // bloom
    };
    final boolean[] enabled = {
            false,   // Normal mapping
            false,   // Specular mapping
            false,   // Dynamic shadows
            true,    // Sorted alpha
            true,    // Fog
            false,    // ANTIALIASING
            false    // bloom
    };

    public GL11DrawContext(GL11Scene parent) {
        this.parent = parent;
    }

    @Override
    public void useMaterial(Material material) {
        parent.useMaterial(material);
    }

    @Override
    public ITesselator createTesselator(int flags, int indiceCount) {
        return new GL11Tesselator(this, flags, indiceCount);
    }

    @Override
    public <T> boolean isSupported(Capability id) {
        return caps[id.ordinal()];
    }

    @Override
    public void setEnabled(Capability id, boolean flag) {
        if (!isSupported(id))
            throw new UnsupportedOperationException("capability not supported");
        enabled[id.ordinal()] = flag;
    }

    @Override
    public boolean isEnabled(Capability id) {
        return enabled[id.ordinal()];
    }
}
