package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.gl.texture.CubeTexture;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.io.res.URLResource;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.geometry.BoundingBox;
import tk.ivybits.engine.scene.node.impl.AbstractActor;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Skybox extends AbstractActor {
    private CubeTexture environmentMap;

    public Skybox(URLResource xpos, URLResource xneg, URLResource ypos, URLResource yneg, URLResource zpos, URLResource zneg) {
        try {
            environmentMap = new CubeTexture(
                    ImageIO.read(xpos.openStream()),
                    ImageIO.read(xneg.openStream()),
                    ImageIO.read(ypos.openStream()),
                    ImageIO.read(yneg.openStream()),
                    ImageIO.read(zpos.openStream()),
                    ImageIO.read(zneg.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return new SkyboxDrawable(this);
    }

    @Override
    public void rotate(float pitch, float yaw, float roll) {
        throw new UnsupportedOperationException();
    }

    public CubeTexture getEnvironmentMap() {
        return environmentMap;
    }
}
