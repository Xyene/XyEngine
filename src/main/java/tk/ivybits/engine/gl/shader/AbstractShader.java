package tk.ivybits.engine.gl.shader;

import static org.lwjgl.opengl.ARBShaderObjects.glDeleteObjectARB;
import static org.lwjgl.opengl.ARBShaderObjects.glUseProgramObjectARB;

public abstract class AbstractShader implements IShader {
    @Override
    public void attach() {
        glUseProgramObjectARB(getShaderHandle());
    }

    @Override
    public void detach() {
        glUseProgramObjectARB(0);
    }

    @Override
    public void destroy() {
        glDeleteObjectARB(getShaderHandle());
    }

    protected abstract int getShaderHandle();
}
