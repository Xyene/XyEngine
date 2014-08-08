package tk.ivybits.engine.scene.node.impl;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.util.ToString;

import static java.lang.Math.toRadians;
import static tk.ivybits.engine.util.ToString.*;

public abstract class AbstractActor implements IActor {
    protected @Printable float x, y, z;
    protected @Printable float sx = 1, sy = 1, sz = 1;
    protected @Printable float pitch, yaw, roll;
    protected Matrix4f modelMatrix = new Matrix4f();
    protected boolean needsNewModelMatrix = false;

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
    public float sx() {
        return sx;
    }

    @Override
    public float sy() {
        return sy;
    }

    @Override
    public float sz() {
        return sz;
    }

    @Override
    public void scale(float sx, float sy, float sz) {
        this.sx = sx;
        this.sy = sy;
        this.sz = sz;
        needsNewModelMatrix = true;
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
        if (needsNewModelMatrix) {
            modelMatrix = new Matrix4f();
            Matrix4f.translate(new Vector3f(x, y, z), modelMatrix, modelMatrix);
            Matrix4f.scale(new Vector3f(sx, sy, sz), modelMatrix, modelMatrix);
            Matrix4f.rotate((float) toRadians(pitch), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
            Matrix4f.rotate((float) toRadians(yaw), new Vector3f(0, 1, 0), modelMatrix, modelMatrix);
            Matrix4f.rotate((float) toRadians(roll), new Vector3f(1, 0, 0), modelMatrix, modelMatrix);
        }
        return modelMatrix;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
