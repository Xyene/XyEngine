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
