package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.ITesselator;
import tk.ivybits.engine.scene.model.node.Material;

public class GL20DrawContext implements IDrawContext {
    final GL20Scene parent;

    public GL20DrawContext(GL20Scene parent) {
        this.parent = parent;
    }

    @Override
    public void useMaterial(Material material) {
        if (parent.getDefaultShader() != null)
            parent.getDefaultShader().useMaterial(material);
    }

    @Override
    public ITesselator createTesselator() {
        return new GL20Tesselator(this);
    }
}
