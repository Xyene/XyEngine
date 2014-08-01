package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.gl.scene.PriorityComparableDrawable;
import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.node.*;

import java.util.*;

public class DefaultSceneNode implements ISceneNode {
    private final DefaultSceneGraph scene;
    private final DefaultSceneNode parent;
    private List<ISceneNode> children = new ArrayList<>();
    private List<IActor> actors = new ArrayList<>();
    private List<ISpotLight> spotLights = new ArrayList<>();
    private List<IPointLight> pointLights = new ArrayList<>();
    private List<IDirectionalLight> dirLights = new ArrayList<>();

    public DefaultSceneNode(DefaultSceneGraph scene) {
        this(scene, null);
    }

    protected DefaultSceneNode(DefaultSceneGraph scene, DefaultSceneNode parent) {
        this.scene = scene;
        this.parent = parent;
    }

    @Override
    public List<ISceneNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public DefaultSceneNode createChild() {
        DefaultSceneNode node = new DefaultSceneNode(scene, this);
        children.add(node);
        return node;
    }

    @Override
    public DefaultSceneNode getParent() {
        return parent;
    }

    @Override
    public ISceneGraph getSceneGraph() {
        return scene;
    }

    @Override
    public List<? extends IActor> getActors() {
        return Collections.unmodifiableList(actors);
    }

    @Override
    public List<ISpotLight> getSpotLights() {
        return Collections.unmodifiableList(spotLights);
    }

    @Override
    public List<IPointLight> getPointLights() {
        return Collections.unmodifiableList(pointLights);
    }

    @Override
    public List<IDirectionalLight> getDirectionalLights() {
        return Collections.unmodifiableList(dirLights);
    }

    @Override
    public ISpotLight createSpotLight() {
        DefaultSpotLight spot = new DefaultSpotLight(this.scene);
        spotLights.add(spot);

        DefaultSceneNode self = this;
        do {
            self.spotLights.add(spot);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("spotLightCreated", spot);
        return spot;
    }

    @Override
    public IPointLight createPointLight() {
        DefaultPointLight point = new DefaultPointLight(this.scene);
        pointLights.add(point);

        DefaultSceneNode self = this;
        do {
            self.pointLights.add(point);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("pointLightCreated", point);
        return point;
    }

    @Override
    public IDirectionalLight createDirectionalLight() {
        DefaultDirectionalLight light = new DefaultDirectionalLight(this.scene);
        dirLights.add(light);

        DefaultSceneNode self = this;
        do {
            self.dirLights.add(light);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("directionalLightCreated", light);
        return light;
    }

    @Override
    public IActor track(IActor actor) {
        DefaultSceneNode self = this;
        do {
            self.actors.add(actor);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("actorAdded", actor);
        return actor;
    }

    @Override
    public void destroy(ISpotLight light) {
        spotLights.remove(light);

        DefaultSceneNode self = this;
        do {
            self.spotLights.remove(light);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("spotLightDestroyed", light);
    }

    @Override
    public void destroy(IPointLight light) {
        pointLights.remove(light);

        DefaultSceneNode self = this;
        do {
            self.pointLights.remove(light);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("pointLightDestroyed", light);
    }

    @Override
    public void destroy(IDirectionalLight light) {
        dirLights.remove(light);

        DefaultSceneNode self = this;
        do {
            self.dirLights.remove(light);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("directionalLightDestroyed", light);
    }

    @Override
    public void destroy(IActor actor) {
        DefaultSceneNode self = this;
        do {
            self.actors.remove(actor);
        } while ((self = self.getParent()) != null);
        scene.fireEvent("actorRemoved", actor);
    }

    @Override
    public void destroy() {
        if (parent == null) {
            throw new UnsupportedOperationException("can't destroy root node");
        }
        // Warning: this will send multiple events for same object if several levels deep
        for (ISpotLight light : spotLights) destroy(light);
        for (IPointLight light : pointLights) destroy(light);
        for (IDirectionalLight light : dirLights) destroy(light);
        for (IActor actor : actors) destroy(actor);
        actors.clear();
        for (ISceneNode node : children)
            node.destroy();
    }
}
