package tk.ivybits.engine.scene.node;

import tk.ivybits.engine.scene.IActor;

import java.util.List;

public interface ISceneNode {
    List<ISceneNode> getChildren();
    ISceneNode createChild();
    ISceneNode getParent();

    ISceneGraph getSceneGraph();

    List<? extends IActor> getActors();
    List<ISpotLight> getSpotLights();
    List<IPointLight> getPointLights();
    List<IDirectionalLight> getDirectionalLights();

    ISpotLight createSpotLight();
    IPointLight createPointLight();
    IDirectionalLight createDirectionalLight();

    IActor track(IActor actor);

    void destroy(ISpotLight light);
    void destroy(IPointLight light);
    void destroy(IDirectionalLight light);
    void destroy(IActor actor);
    void destroy();
}
