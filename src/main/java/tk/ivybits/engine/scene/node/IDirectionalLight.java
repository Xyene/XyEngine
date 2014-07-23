package tk.ivybits.engine.scene.node;

import java.awt.*;

public interface IDirectionalLight {
    IDirectionalLight setRotation(float pitch, float yaw);

    float pitch();

    float yaw();

    float dx();

    float dy();

    float dz();

    IDirectionalLight setAmbientColor(Color ambientColor);

    IDirectionalLight setDiffuseColor(Color diffuseColor);

    IDirectionalLight setSpecularColor(Color specularColor);

    IDirectionalLight setIntensity(float intensity);

    Color getAmbientColor();

    Color getDiffuseColor();

    Color getSpecularColor();

    float getIntensity();
}
