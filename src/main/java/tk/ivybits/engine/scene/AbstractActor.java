package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.gl.shader.IShader;

import javax.vecmath.Vector3f;

public abstract class AbstractActor implements IActor {
    private float x, y, z;
    private float pitch, yaw, roll;

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
    public Vector3f position() {
        return new Vector3f(x, y, z);
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
}
