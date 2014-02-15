package tk.ivybits.engine.scene.light;

import java.awt.*;

public class PointLight {
    private float x, y, z;
    private Color ambientColor = Color.BLACK;
    private Color diffuseColor = Color.WHITE;
    private Color specularColor = Color.WHITE;
    private float intensity = 1;

    public PointLight() {
    }

    public void setPosition(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public float z() {
        return z;
    }

    public Color getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
