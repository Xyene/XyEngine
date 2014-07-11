package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.node.Material;

public interface IDrawContext {
    // Maybe make these instance of BoolProperty, IntProperty so generic abuse isn't needed
    int NORMAL_MAPS = 0,
            SPECULAR_MAPS = 1,
            DYNAMIC_SHADOWS = 2,
            DEFERRED_RENDERING = 3,
            SORTED_ALPHA = 4,
            BLOOM = 5,
            MAX_LIGHTS = 6;

    void useMaterial(Material material);

    ITesselator createTesselator();

    <T> T getCapability(int id);

    void setEnabled(int id, boolean flag);

    boolean isEnabled(int property);
}
