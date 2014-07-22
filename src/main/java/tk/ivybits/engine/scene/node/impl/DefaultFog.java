package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.scene.node.IFog;

import java.awt.*;

public class DefaultFog implements IFog {
    private DefaultSceneGraph scene;
    private Color fogColor = Color.WHITE;
    private float fogNear = 200, fogFar = 600;

    public DefaultFog(DefaultSceneGraph scene) {
        this.scene = scene;
    }

    public Color getFogColor() {
        return fogColor;
    }

    public DefaultFog setFogColor(Color fogColor) {
        this.fogColor = fogColor;
        scene.fireEvent("fogUpdated", this);
        return this;
    }

    public float getFogNear() {
        return fogNear;
    }

    public DefaultFog setFogNear(float fogNear) {
        this.fogNear = fogNear;
        scene.fireEvent("fogUpdated", this);
        return this;
    }

    public float getFogFar() {
        return fogFar;
    }

    public DefaultFog setFogFar(float fogFar) {
        this.fogFar = fogFar;
        scene.fireEvent("fogUpdated", this);
        return this;
    }
}
