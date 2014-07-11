package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.scene.IActor;
import tk.ivybits.engine.scene.IDrawable;

class PriorityComparableDrawable {
    public final IActor wrapped;
    public final IDrawable draw;
    public final int priority;

    public PriorityComparableDrawable(IActor actor, IDrawable draw, int priority) {
        this.wrapped = actor;
        this.draw = draw;
        this.priority = priority;
    }
}
