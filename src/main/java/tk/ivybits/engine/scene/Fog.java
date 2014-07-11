package tk.ivybits.engine.scene;

import java.awt.*;

public class Fog {
    private Color fogColor;
    private float fogNear, fogFar;

    public Color getFogColor() {
        return fogColor;
    }

    public Fog setFogColor(Color fogColor) {
        this.fogColor = fogColor;
        return this;
    }

    public float getFogNear() {
        return fogNear;
    }

    public Fog setFogNear(float fogNear) {
        this.fogNear = fogNear;
        return this;
    }

    public float getFogFar() {
        return fogFar;
    }

    public Fog setFogFar(float fogFar) {
        this.fogFar = fogFar;
        return this;
    }
}
