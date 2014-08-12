package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.gl.texture.CubeTexture;
import tk.ivybits.engine.gl.texture.Texture;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IScene;

public class StaticEnvironmentMap implements IEnvironmentMap {
    private final CubeTexture cubemap;

    public StaticEnvironmentMap(CubeTexture cubemap) {
        this.cubemap = cubemap;
    }

    @Override
    public CubeTexture getFor(IScene scene, IActor actor) {
        return cubemap;
    }
}
