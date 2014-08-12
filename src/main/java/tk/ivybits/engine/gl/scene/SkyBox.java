package tk.ivybits.engine.gl.scene;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.geometry.BoundingBox;
import tk.ivybits.engine.scene.node.impl.AbstractActor;

import java.awt.image.BufferedImage;

public class SkyBox extends AbstractActor {
    final BufferedImage xpos;
    final BufferedImage xneg;
    final BufferedImage ypos;
    final BufferedImage yneg;
    final BufferedImage zpos;
    final BufferedImage zneg;

    public SkyBox(BufferedImage xpos, BufferedImage xneg, BufferedImage ypos, BufferedImage yneg, BufferedImage zpos, BufferedImage zneg) {
        this.xpos = xpos;
        this.xneg = xneg;
        this.ypos = ypos;
        this.yneg = yneg;
        this.zpos = zpos;
        this.zneg = zneg;

        scale(20, 20, 20);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public float pitch() {
        return 0;
    }

    @Override
    public float yaw() {
        return 0;
    }

    @Override
    public float roll() {
        return 0;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }

    @Override
    public IDrawable createDrawable(IDrawContext context) {
        return new SkyBoxDrawable(this);
    }

    @Override
    public void rotate(float pitch, float yaw, float roll) {
        throw new UnsupportedOperationException();
    }
}
