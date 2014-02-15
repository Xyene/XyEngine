package tk.ivybits.engine.scene;

import java.awt.*;

public class Fog {
    private Color fogColor;
    private float fogNear, fogFar;

    public Color getFogColor() {
        return fogColor;
    }

    public void setFogColor(Color fogColor) {
        this.fogColor = fogColor;
    }

    public float getFogNear() {
        return fogNear;
    }

    public void setFogNear(float fogNear) {
        this.fogNear = fogNear;
    }

    public float getFogFar() {
        return fogFar;
    }

    public void setFogFar(float fogFar) {
        this.fogFar = fogFar;
    }
}
