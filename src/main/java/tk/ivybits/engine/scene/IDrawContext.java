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

import java.util.List;

public interface IDrawContext {
    public static enum Capability {
        NORMAL_MAPPING, PARALLAX_MAPPING, SPECULAR_MAPPING, OBJECT_SHADOWS, ALPHA_TESTING, FOG, ANTIALIASING, BLOOM, REFLECTIONS;

        public static class Key<T> {
            public static final Key<Float> HDR_BLOOM_EXPOSURE = new Key<>();
            public static final Key<Float> HDR_BLOOM_FACTOR = new Key<>();
            public static final Key<Float> HDR_BLOOM_CAP = new Key<>();

            public static final Key<Integer> AA_SAMPLE_COUNT = new Key<>();
            public static final Key<List<Integer>> AA_SAMPLE_SIZES = new Key<>();
        }
    }

    void useMaterial(Material material);

    ITesselator createTesselator(int flags, int type);

    boolean isSupported(Capability cap);

    <T> T getOption(Capability.Key<T> key);

    <T> void setOption(Capability.Key<T> key, T value);

    void setEnabled(Capability cap, boolean flag);

    boolean isEnabled(Capability cap);
}
