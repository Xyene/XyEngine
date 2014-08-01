package tk.ivybits.engine.scene.event;

import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.node.IDirectionalLight;
import tk.ivybits.engine.scene.node.IFog;
import tk.ivybits.engine.scene.node.IPointLight;
import tk.ivybits.engine.scene.node.ISpotLight;

public interface ISceneChangeListener {
    void spotLightCreated(ISpotLight light);

    void pointLightCreated(IPointLight light);

    void directionalLightCreated(IDirectionalLight light);

    void spotLightUpdated(ISpotLight light);

    void pointLightUpdated(IPointLight light);

    void directionalLightUpdated(IDirectionalLight light);

    void spotLightDestroyed(ISpotLight light);

    void pointLightDestroyed(IPointLight light);

    void directionalLightDestroyed(IDirectionalLight light);

    void fogUpdated(IFog fog);

    void actorAdded(IActor actor);

    void actorRemoved(IActor actor);
}
