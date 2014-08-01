package tk.ivybits.engine.scene.event;

import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.node.IDirectionalLight;
import tk.ivybits.engine.scene.node.IFog;
import tk.ivybits.engine.scene.node.IPointLight;
import tk.ivybits.engine.scene.node.ISpotLight;

public abstract class SceneChangeAdapter implements ISceneChangeListener {
    @Override
    public void spotLightCreated(ISpotLight light) {

    }

    @Override
    public void pointLightCreated(IPointLight light) {

    }

    @Override
    public void directionalLightCreated(IDirectionalLight light) {

    }

    @Override
    public void spotLightUpdated(ISpotLight light) {

    }

    @Override
    public void pointLightUpdated(IPointLight light) {

    }

    @Override
    public void directionalLightUpdated(IDirectionalLight light) {

    }

    @Override
    public void spotLightDestroyed(ISpotLight light) {

    }

    @Override
    public void pointLightDestroyed(IPointLight light) {

    }

    @Override
    public void directionalLightDestroyed(IDirectionalLight light) {

    }

    @Override
    public void fogUpdated(IFog fog) {

    }

    @Override
    public void actorAdded(IActor actor) {

    }

    @Override
    public void actorRemoved(IActor actor) {

    }
}
