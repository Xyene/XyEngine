package tk.ivybits.engine.scene.node;

import java.awt.*;

public interface IPointLight {
    IPointLight setPosition(float x, float y, float z);

    float x();

    float y();

    float z();

    Color getAmbientColor();

    IPointLight setAmbientColor(Color ambientColor);

    Color getDiffuseColor();

    IPointLight setDiffuseColor(Color diffuseColor);

    Color getSpecularColor();

    IPointLight setSpecularColor(Color specularColor);

    float getIntensity();

    IPointLight setIntensity(float intensity);
}
