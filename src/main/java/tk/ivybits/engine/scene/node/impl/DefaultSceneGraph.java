package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.scene.node.*;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class DefaultSceneGraph implements ISceneGraph {
    private final IAtmosphere atmosphere;
    private final ISceneNode root;
    private List<ISceneChangeListener> listenerList = new LinkedList<>();

    public DefaultSceneGraph() {
        this.atmosphere = new DefaultAtmosphere(this);
        this.root = new DefaultSceneNode(this);
    }

    public void fireEvent(String name, Object arg) {
        Method[] methods = ISceneChangeListener.class.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(name)) {
                for (ISceneChangeListener listener : listenerList) {
                    try {
                        m.invoke(listener, arg);
                    } catch (ReflectiveOperationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public ISceneNode getRoot() {
        return this.root;
    }

    @Override
    public IAtmosphere getAtmosphere() {
        return this.atmosphere;
    }

    @Override
    public void addSceneChangeListener(ISceneChangeListener listener) {
        addSceneChangeListener(listener, false);
    }

    @Override
    public void addSceneChangeListener(ISceneChangeListener listener, boolean fireCreateEvents) {
        listenerList.add(listener);
        if (!fireCreateEvents) return;
        for (ISpotLight light : root.getSpotLights()) listener.spotLightCreated(light);
        for (IPointLight light : root.getPointLights()) listener.pointLightCreated(light);
        for (IDirectionalLight light : root.getDirectionalLights()) listener.directionalLightCreated(light);
    }

    @Override
    public void removeSceneChangeListener(ISceneChangeListener listener) {
        listenerList.remove(listener);
    }
}
