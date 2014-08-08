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
