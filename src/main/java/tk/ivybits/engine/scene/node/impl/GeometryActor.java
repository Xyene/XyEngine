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

import tk.ivybits.engine.gl.scene.GeometryDrawable;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.geometry.BoundingBox;
import tk.ivybits.engine.scene.model.Model;

public class GeometryActor extends AbstractActor {
    protected Model model;
    protected BoundingBox boundingBox;
    protected int priority = Integer.MAX_VALUE;

    public GeometryActor(Model model) {
        this.model = model;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox != null ? boundingBox : (boundingBox = BoundingBox.getBoundingBox(model));
    }

    @Override
    public IDrawable createDrawable(IDrawContext context) {
        return new GeometryDrawable(context, model, priority);
    }
}
