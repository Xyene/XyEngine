package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.scene.node.IAtmosphere;
import tk.ivybits.engine.scene.node.IFog;

public class DefaultAtmosphere implements IAtmosphere {
    private final DefaultSceneGraph scene;
    private final DefaultFog fog;

    public DefaultAtmosphere(DefaultSceneGraph scene) {
        this.scene = scene;
        this.fog = new DefaultFog(scene);
    }

    @Override
    public IFog getFog() {
        return fog;
    }
}
