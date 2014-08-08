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

package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawable;

import java.util.Comparator;

public class PriorityComparableDrawable {
    /**
     * A sorting comparator that sorts collections of {@link tk.ivybits.engine.gl.scene.PriorityComparableDrawable}
     * based on declared priority, highest to lowest.
     */
    public static Comparator<PriorityComparableDrawable> COMPARATOR = new Comparator<PriorityComparableDrawable>() {
        @Override
        public int compare(PriorityComparableDrawable o1, PriorityComparableDrawable o2) {
            return o2.drawable.priority() - o1.drawable.priority();
        }
    };

    public final IActor actor;
    public final IDrawable drawable;

    public PriorityComparableDrawable(IActor actor, IDrawable drawable) {
        this.actor = actor;
        this.drawable = drawable;
    }
}
