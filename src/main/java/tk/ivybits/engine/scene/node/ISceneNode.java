package tk.ivybits.engine.scene.node;

import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.light.PointLight;
import tk.ivybits.engine.scene.light.SpotLight;
import tk.ivybits.engine.scene.model.IGeometry;

import java.util.List;
import java.util.Set;

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
