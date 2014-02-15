package tk.ivybits.engine.scene;

import tk.ivybits.engine.gl.scene.gl20.GL20Scene;

public class SceneFactory {
    public static IScene createScene() {
        return new GL20Scene();
    }
}
