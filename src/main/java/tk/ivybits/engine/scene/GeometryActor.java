package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.GeometryDrawable;
import tk.ivybits.engine.scene.model.node.Mesh;
import tk.ivybits.engine.scene.model.node.Model;

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
