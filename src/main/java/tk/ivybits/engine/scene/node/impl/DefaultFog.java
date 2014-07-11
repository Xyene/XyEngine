package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.gl.scene.gl20.GL20Scene;
import tk.ivybits.engine.scene.node.IFog;

import java.awt.*;

public class DefaultFog implements IFog {
    private DefaultSceneGraph scene;
    private boolean enabled;
    private Color fogColor;
    private float fogNear, fogFar;

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

    @Override
    public IFog setEnabled(boolean flag) {
        enabled = flag;
        scene.fireEvent("fogUpdated", this);
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
