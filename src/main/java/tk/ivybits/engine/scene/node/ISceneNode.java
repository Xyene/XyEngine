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

    <T extends IActor> T track(T actor);

    void destroy(ISpotLight light);

    void destroy(IPointLight light);

    void destroy(IDirectionalLight light);

    void destroy(IActor actor);

    void destroy();
}
