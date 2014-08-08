package sandbox;

import tk.ivybits.engine.io.model.ModelIO;
import tk.ivybits.engine.scene.model.Model;
import tk.ivybits.engine.scene.node.impl.GeometryActor;

import java.io.IOException;

public class MagicCircleActor extends GeometryActor {
    private float size = 1;
    private int mod = 1;

    public MagicCircleActor() throws IOException {
        super(ModelIO.readSystem("tk/ivybits/engine/game/model/magic-circle-1.obj"));
        priority = 0;
    }

    @Override
    public void update(float delta) {
        size += 0.01f * mod;
        if(size > 2 || size < 1) mod *= -1;
        scale(size, size, size);
        rotate(pitch, yaw + 0.2f, roll);
    }
}
