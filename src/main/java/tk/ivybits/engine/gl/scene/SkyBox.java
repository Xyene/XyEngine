package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.gl.texture.CubeTexture;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.geometry.BoundingBox;
import tk.ivybits.engine.scene.node.impl.AbstractActor;

import java.awt.image.BufferedImage;

public class Skybox extends AbstractActor {
    private CubeTexture environmentMap;

    public Skybox(BufferedImage xpos, BufferedImage xneg, BufferedImage ypos, BufferedImage yneg, BufferedImage zpos, BufferedImage zneg) {
        environmentMap = new CubeTexture(xpos, xneg, ypos, yneg, zpos, zneg);
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
