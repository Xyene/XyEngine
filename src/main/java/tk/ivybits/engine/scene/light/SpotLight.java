package tk.ivybits.engine.scene.light;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class SpotLight extends PointLight {
    private float pitch;
    private float yaw;
    private float cutoff;
    private float intensity;

    public void setRotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public float pitch() {
        return pitch;
    }

    public float yaw() {
        return yaw;
    }

    public float dx() {
        return (float) (cos(toRadians(yaw - 90)) * m());
    }

    public float dy() {
        return (float) -sin(toRadians(pitch));
    }

    public float dz() {
        return (float) sin(toRadians(yaw - 90)) * m();
    }

    private final float m() {
        return (float) cos(toRadians(pitch));
    }
    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
