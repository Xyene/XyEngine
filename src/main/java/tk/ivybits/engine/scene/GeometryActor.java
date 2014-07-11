package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.GeometryDrawable;
import tk.ivybits.engine.scene.model.IGeometry;

public class GeometryActor extends AbstractActor {
    private IGeometry model;

    public GeometryActor(IGeometry model) {
        this.model = model;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public IDrawable createDrawable(IDrawContext context) {
        return new GeometryDrawable(context, model);
    }
}
