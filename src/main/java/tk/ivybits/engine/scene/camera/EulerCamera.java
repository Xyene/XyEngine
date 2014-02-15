package tk.ivybits.engine.scene.camera;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class EulerCamera implements ICamera {
    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float pitch = 0;
    private float yaw = 0;
    private float roll = 0;
    private float fov = 90;
    private float aspectRatio = 1;
    private float zNear;
    private float zFar;

    public EulerCamera() {

    }


    @Override
    public void move(float dx, float dy, float dz) {
        this.z += dx * cos(toRadians(this.yaw - 90)) + dz * cos(toRadians(this.yaw));
        this.x -= dx * sin(toRadians(this.yaw - 90)) + dz * sin(toRadians(this.yaw));
        this.y += dy * sin(toRadians(this.pitch - 90)) + dz * sin(toRadians(this.pitch));
    }

    @Override
    public ICamera setRotation(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
        return this;
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

    public void applyPerspectiveMatrix() {
        glPushAttrib(GL_TRANSFORM_BIT);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(fov, aspectRatio, zNear, zFar);
        glPopAttrib();
    }

    @Override
    public ICamera setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        applyPerspectiveMatrix();
        return this;
    }

    @Override
    public ICamera setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public ICamera setFieldOfView(float fov) {
        this.fov = fov;
        applyPerspectiveMatrix();
        return this;
    }

    @Override
    public ICamera setZFar(float zFar) {
        this.zFar = zFar;
        applyPerspectiveMatrix();
        return this;
    }

    @Override
    public ICamera setZNear(float zNear) {
        this.zNear = zNear;
        applyPerspectiveMatrix();
        return this;
    }

    @Override
    public float dx() {
        return (float) (cos(toRadians(yaw - 90)) * m());
    }

    @Override
    public float dy() {
        return (float) -sin(toRadians(pitch));
    }

    @Override
    public float dz() {
        return (float) sin(toRadians(yaw - 90)) * m();
    }

    private final float m() {
        return (float) cos(toRadians(pitch));
    }
}