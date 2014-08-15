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

package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.opengl.GLContext.getCapabilities;
import static tk.ivybits.engine.gl.GL.*;

public class GL20DrawContext implements IDrawContext {
    final GL20Scene parent;
    private final boolean[] caps;
    private final boolean[] enabled;
    private final HashMap<Capability.Key, Object> settings = new HashMap<>();

    public GL20DrawContext(GL20Scene parent) {
        this.parent = parent;
        caps = new boolean[]{
                true,  // Normal mapping
                true,  // Specular mapping
                getCapabilities().OpenGL30, // Dynamic shadows
                true,  // alpha testing
                true,    // Fog
                getCapabilities().GL_EXT_framebuffer_object
                        && getCapabilities().GL_EXT_framebuffer_multisample
                        && getCapabilities().GL_ARB_texture_multisample
                        && getCapabilities().GL_EXT_framebuffer_blit,    // ANTIALIASING
                getCapabilities().GL_EXT_framebuffer_object,    // bloom
                true // Reflections
        };
        settings.put(Capability.Key.HDR_BLOOM_EXPOSURE, 2f);
        settings.put(Capability.Key.HDR_BLOOM_FACTOR, 0.5f);
        settings.put(Capability.Key.HDR_BLOOM_CAP, 5f);

        settings.put(Capability.Key.AA_SAMPLE_COUNT, glGetInteger(GL_MAX_SAMPLES) / 2);

        List<Integer> sizes = new ArrayList<>();
        int size = 1;
        while(size < glGetInteger(GL_MAX_SAMPLES)) {
            sizes.add( size *= 2);
        }
        settings.put(Capability.Key.AA_SAMPLE_SIZES, sizes);

        enabled = new boolean[caps.length];
        System.arraycopy(caps, 0, enabled, 0, caps.length);
    }

    @Override
    public void useMaterial(Material material) {
        if (parent.currentGeometryShader != null)
            parent.currentGeometryShader.useMaterial(material);
    }

    @Override
    public ITesselator createTesselator(int flags, int type) {
        return new GL20Tesselator(this, flags, type);
    }

    @Override
    public boolean isSupported(Capability id) {
        return caps[id.ordinal()];
    }

    @Override
    public <T> T getOption(Capability.Key<T> key) {
        return (T) settings.get(key);
    }

    @Override
    public <T> void setOption(Capability.Key<T> key, T value) {
        settings.put(key, value);
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
