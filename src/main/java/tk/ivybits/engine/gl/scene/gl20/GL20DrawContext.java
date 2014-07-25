package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

public class GL20DrawContext implements IDrawContext {
    final GL20Scene parent;
    final boolean[] caps;
    final boolean[] enabled;

    public GL20DrawContext(GL20Scene parent) {
        this.parent = parent;
        caps = new boolean[]{
                true,  // Normal mapping
                true,  // Specular mapping
                GLContext.getCapabilities().OpenGL30, // Dynamic shadows
                true,  // alpha testing
                true,    // Fog
                GLContext.getCapabilities().OpenGL30,    // ANTIALIASING
                true    // bloom
        };
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
