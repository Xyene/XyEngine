/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package tk.ivybits.engine.scene.node.impl;

import tk.ivybits.engine.scene.event.ISceneChangeListener;
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
