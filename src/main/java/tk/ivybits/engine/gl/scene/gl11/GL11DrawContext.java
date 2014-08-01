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
