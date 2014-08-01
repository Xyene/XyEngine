package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.gl.scene.GeometryDrawable;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.geometry.BoundingBox;
import tk.ivybits.engine.scene.model.Model;

public class GeometryActor extends AbstractActor {
    private Model model;
    private BoundingBox boundingBox;

    public GeometryActor(Model model) {
        this.model = model;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox != null ? boundingBox : (boundingBox = BoundingBox.getBoundingBox(model));
    }

    @Override
    public IDrawable createDrawable(IDrawContext context) {
        return new GeometryDrawable(context, model);
    }
}
