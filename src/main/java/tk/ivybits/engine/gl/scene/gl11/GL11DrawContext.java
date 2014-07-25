package tk.ivybits.engine.gl.scene.gl11;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

public class GL11DrawContext implements IDrawContext {
    final GL11Scene parent;
    final boolean[] CAPABILITIES = {
            false,   // Normal mapping
            false,   // Specular mapping
            false,   // Dynamic shadows
            true,    // Sorted alpha
            true,    // Fog
            false,    // ANTIALIASING todo: glSmoothHint etc
            false    // bloom
    };
    final boolean[] ENABLED_CAPABILITIES = {
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
    public boolean isSupported(int id) {
        if (id < CAPABILITIES.length) return CAPABILITIES[id];
        else throw new IllegalArgumentException("no such capability"); // Maybe return null instead?
    }

    @Override
    public void setEnabled(int id, boolean flag) {
        if(!isSupported(id))
            throw new UnsupportedOperationException("capability not supported");
        if (id < ENABLED_CAPABILITIES.length) ENABLED_CAPABILITIES[id] = flag;
        else throw new IllegalArgumentException("no such capability"); // Maybe return null instead?
    }

    @Override
    public boolean isEnabled(int id) {
        if (id < ENABLED_CAPABILITIES.length) return ENABLED_CAPABILITIES[id];
        else throw new IllegalArgumentException("no such capability"); // Maybe return null instead?
    }
}
