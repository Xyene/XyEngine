package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.gl.shader.IShader;

public class DefaultActor implements IActor {
    private IGeometry model;
    private float x, y, z;
    private float pitch, yaw, roll;

    public DefaultActor(IGeometry model) {
        this.model = model;
    }

    @Override
    public void position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float z() {
        return z;
    }

    @Override
    public void rotate(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @Override
    public float yaw() {
        return yaw;
    }

    @Override
    public float roll() {
        return roll;
    }

    @Override
    public IGeometry getGeometry() {
        return model;
    }

    @Override
    public void setShader(IShader shader) {

    }
}
