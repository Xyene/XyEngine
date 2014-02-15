package tk.ivybits.engine.scene.light;

public class SpotLight extends PointLight {
    private float dx, dy, dz;
    private float cutoff;
    private float intensity;

    public void setDirection(float dx, float dy, float dz) {
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public float dx() {
        return dx;
    }

    public float dy() {
        return dy;
    }

    public float dz() {
        return dz;
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
