package tk.ivybits.engine.scene;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import static java.lang.Math.toRadians;

public abstract class AbstractActor implements IActor {
    protected float x, y, z;
    protected float pitch, yaw, roll;
    protected Matrix4f modelMatrix = new Matrix4f();
    private boolean needsNewModelMatrix = false;

    @Override
    public void position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        needsNewModelMatrix = true;
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
        needsNewModelMatrix = true;
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
    public Matrix4f getTransform() {
        if(needsNewModelMatrix) {
            modelMatrix = new Matrix4f();
            Matrix4f.translate(new Vector3f(x, y, z), modelMatrix, modelMatrix);
            Matrix4f.rotate((float) toRadians(pitch), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
            Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
            Matrix4f.rotate((float) toRadians(roll), new Vector3f(1, 0, 0), modelMatrix, modelMatrix);
        }
        return modelMatrix;
    }
}
