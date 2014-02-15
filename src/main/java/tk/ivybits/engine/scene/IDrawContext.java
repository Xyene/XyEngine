package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.node.Material;

public interface IDrawContext {
    void useMaterial(Material material);

    ITesselator createTesselator();
}
