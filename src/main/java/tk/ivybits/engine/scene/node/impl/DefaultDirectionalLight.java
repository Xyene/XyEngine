package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.scene.node.IDirectionalLight;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class DefaultDirectionalLight implements IDirectionalLight {
    private final DefaultSceneGraph scene;
    private Color ambientColor = Color.BLACK;
    private Color diffuseColor = Color.WHITE;
    private Color specularColor = Color.WHITE;
    private float pitch;
    private float yaw;
    private float intensity;

    public DefaultDirectionalLight(DefaultSceneGraph scene) {
        this.scene = scene;
    }

    public DefaultDirectionalLight setRotation(float pitch, float yaw) {
        this.pitch = pitch;
        this.yaw = yaw;
        scene.fireEvent("directionalLightUpdated", this);
        return this;
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

    private float m() {
        return (float) cos(toRadians(pitch));
    }

    public Color getAmbientColor() {
        return ambientColor;
    }

    public DefaultDirectionalLight setAmbientColor(Color ambientColor) {
        this.ambientColor = ambientColor;
        scene.fireEvent("directionalLightUpdated", this);
        return this;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public DefaultDirectionalLight setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
        scene.fireEvent("directionalLightUpdated", this);
        return this;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public DefaultDirectionalLight setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
        scene.fireEvent("directionalLightUpdated", this);
        return this;
    }

    public float getIntensity() {
        return intensity;
    }

    public DefaultDirectionalLight setIntensity(float intensity) {
        this.intensity = intensity;
        scene.fireEvent("directionalLightUpdated", this);
        return this;
    }
}
