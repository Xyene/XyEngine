package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

public class GL20DrawContext implements IDrawContext {
    final GL20Scene parent;
    final boolean[] CAPABILITIES = {
            true,  // Normal mapping
            true,  // Specular mapping
            true, // Dynamic shadows
            true,  // alpha testing
            true,    // Fog
            true,    // ANTIALIASING
            true    // bloom
    };
    final boolean[] ENABLED_CAPABILITIES = {
            true,  // Normal mapping
            true,  // Specular mapping
            true, // Dynamic shadows
            true,  // alpha testing
            true,    // Fog
            true,    // ANTIALIASING
            true     // bloom
    };

    public GL20DrawContext(GL20Scene parent) {
        this.parent = parent;
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
    public <T> boolean isSupported(int id) {
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
