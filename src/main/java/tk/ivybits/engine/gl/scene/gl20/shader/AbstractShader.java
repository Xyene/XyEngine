package tk.ivybits.engine.gl.scene.gl20.shader;

import static tk.ivybits.engine.gl.GL.*;
public abstract class AbstractShader implements ISceneShader {
    protected boolean isAttached = false;
    @Override
    public void attach() {
        isAttached = true;
        glUseProgram(getShaderHandle());
    }

    @Override
    public void detach() {
        isAttached = false;
        glUseProgram(0);
    }

    @Override
    public void destroy() {
        glDeleteShader(getShaderHandle());
    }

    protected abstract int getShaderHandle();
}