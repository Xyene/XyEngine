package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

public interface IDrawContext {
    // Maybe make these instance of BoolProperty, IntProperty so generic abuse isn't needed
    int NORMAL_MAPS = 0,
            SPECULAR_MAPS = 1,
            OBJECT_SHADOWS = 2,
            ALPHA_TESTING = 3,
            FOG = 4,
            ANTIALIASING = 5,
            BLOOM = 6;

    void useMaterial(Material material);

    ITesselator createTesselator(int flags, int type);

    <T> boolean isSupported(int id);

    void setEnabled(int id, boolean flag);

    boolean isEnabled(int property);
}
