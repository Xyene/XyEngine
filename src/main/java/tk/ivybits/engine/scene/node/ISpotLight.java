package tk.ivybits.engine.scene.node;

import java.awt.*;

public interface ISpotLight extends IPointLight, IDirectionalLight {
    float getCutoff();

    ISpotLight setCutoff(float cutoff);

    ISpotLight setPosition(float x, float y, float z);

    ISpotLight setAmbientColor(Color ambientColor);

    ISpotLight setDiffuseColor(Color diffuseColor);

    ISpotLight setSpecularColor(Color specularColor);

    ISpotLight setIntensity(float intensity);

    ISpotLight setRotation(float pitch, float yaw);
}
