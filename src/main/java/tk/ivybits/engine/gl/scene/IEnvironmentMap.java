package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.gl.texture.CubeTexture;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IScene;

public interface IEnvironmentMap {
    CubeTexture getFor(IScene scene, IActor actor);
}
