package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

public class GL20DrawContext implements IDrawContext {
    final GL20Scene PARENT;
    final Object[] CAPABILITIES = {
            true,  // Normal mapping
            true,  // Specular mapping
            true, // Dynamic shadows
            false, // Deferred rendering
            true,  // Sorted alpha
            true,  // Bloom
            64     // Max lights
    };
    final boolean[] ENABLED_CAPABILITIES = {
            true,  // Normal mapping
            true,  // Specular mapping
            true, // Dynamic shadows
            false, // Deferred rendering
            true,  // Sorted alpha
            true  // Bloom
    };

    public GL20DrawContext(GL20Scene parent) {
        this.PARENT = parent;
    }

    @Override
    public void useMaterial(Material material) {
        if (PARENT.currentGeometryShader != null)
            PARENT.currentGeometryShader.useMaterial(material);
    }

    @Override
    public ITesselator createTesselator() {
        return new GL20Tesselator(this);
    }

    @Override
    public <T> T getCapability(int id) {
        if (id < CAPABILITIES.length) return (T) CAPABILITIES[id];
        else throw new IllegalArgumentException("no such capability"); // Maybe return null instead?
    }

    @Override
    public void setEnabled(int id, boolean flag) {
        if (id < ENABLED_CAPABILITIES.length) ENABLED_CAPABILITIES[id] = flag;
        else throw new IllegalArgumentException("no such capability"); // Maybe return null instead?
    }

    @Override
    public boolean isEnabled(int id) {
        if (id < ENABLED_CAPABILITIES.length) return ENABLED_CAPABILITIES[id];
        else throw new IllegalArgumentException("no such capability"); // Maybe return null instead?
    }
}
