package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

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
