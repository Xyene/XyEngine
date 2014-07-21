package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawable;

import java.util.Comparator;

public class PriorityComparableDrawable {
    public static Comparator<PriorityComparableDrawable> COMPARATOR = new Comparator<PriorityComparableDrawable>() {
        @Override
        public int compare(PriorityComparableDrawable o1, PriorityComparableDrawable o2) {
            return o1.priority - o2.priority;
        }
    };
    public final IActor wrapped;
    public final IDrawable draw;
    public final int priority;

    public PriorityComparableDrawable(IActor actor, IDrawable draw, int priority) {
        this.wrapped = actor;
        this.draw = draw;
        this.priority = priority;
    }
}
