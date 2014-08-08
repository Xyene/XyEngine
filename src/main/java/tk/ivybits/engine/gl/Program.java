package tk.ivybits.engine.gl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.vector.*;

import java.lang.ref.WeakReference;
import java.nio.FloatBuffer;
import java.util.*;

import static tk.ivybits.engine.gl.GL.*;

public class Program {
    private final int handle;
    private final HashMap<String, Integer> uniforms = new HashMap<>();
    private final HashMap<String, Integer> attributes = new HashMap<>();
    private int attachDepth = 0;
    private int lastShader;

    public Program(int handle) {
        this.handle = handle;
    }

    public int getUniformLocation(String name) {
        Integer loc = uniforms.get(name);
        if (loc == null) {
            attach();
            loc = glGetUniformLocation(handle, name);
            detach();
            uniforms.put(name, loc);
        }
        return loc;
    }

    public int getAttributeLocation(String name) {
        Integer loc = attributes.get(name);
        if (loc == null) {
            attach();
            loc = glGetAttribLocation(handle, name);
            detach();
            attributes.put(name, loc);
        }
        return loc;
    }

    private Class ensureConstantTypes(Object[] objs) {
        Class c = objs[0].getClass();
        for (int n = 1; n < objs.length; n++)
            if (objs[n].getClass() != c)
                throw new IllegalArgumentException("values not of constant type");
        return c;
    }

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public void setUniform(String handle, Object... objs) {
        setUniform(getUniformLocation(handle), objs);
    }

    public void setUniform(int handle, Object... objs) {
        if (handle < 0)
            return;
        attach();
        if (objs.length == 1) {
            Object obj = objs[0];
            if (obj instanceof Matrix2f || obj instanceof Matrix3f || obj instanceof Matrix4f) {
                matrixBuffer.clear();
                ((Matrix) obj).store(matrixBuffer);
                matrixBuffer.flip();
            }
            if (obj instanceof Matrix2f)
                glUniformMatrix2(handle, false, matrixBuffer);
            else if (obj instanceof Matrix3f)
                glUniformMatrix3(handle, false, matrixBuffer);
            else if (obj instanceof Matrix4f)
                glUniformMatrix4(handle, false, matrixBuffer);
            else if (obj instanceof Boolean)
                glUniform1i(handle, ((boolean) obj) ? 1 : 0);
            else if (obj instanceof Vector2f)
                glUniform2f(handle, ((Vector2f) obj).x, ((Vector2f) obj).y);
            else if (obj instanceof Vector3f)
                glUniform3f(handle, ((Vector3f) obj).x, ((Vector3f) obj).y, ((Vector3f) obj).z);
            else if (obj instanceof Vector4f)
                glUniform4f(handle, ((Vector4f) obj).x, ((Vector4f) obj).y, ((Vector4f) obj).z, ((Vector4f) obj).w);
            else if (obj instanceof Float)
                glUniform1f(handle, (Float) obj);
            else if (obj instanceof Integer)
                glUniform1i(handle, (Integer) obj);
            else {
                detach();
                throw new UnsupportedOperationException("cannot perform automatic type conversion on object of type " + obj.getClass().getSimpleName());
            }
            detach();
            return;
        }
        Class type = ensureConstantTypes(objs);
        if (type == Float.class) {
            switch (objs.length) {
                case 2:
                    glUniform2f(handle, (float) objs[0], (float) objs[1]);
                    break;
                case 3:
                    glUniform3f(handle, (float) objs[0], (float) objs[1], (float) objs[2]);
                    break;
                case 4:
                    glUniform4f(handle, (float) objs[0], (float) objs[1], (float) objs[2], (float) objs[3]);
                    break;
                default:
                    detach();
                    throw new UnsupportedOperationException("can only convert float arrays of length 2, 3, or 4");
            }
        } else if (type == Integer.class) {
            switch (objs.length) {
                case 2:
                    glUniform2i(handle, (int) objs[0], (int) objs[1]);
                    break;
                case 3:
                    glUniform3i(handle, (int) objs[0], (int) objs[1], (int) objs[2]);
                    break;
                case 4:
                    glUniform4i(handle, (int) objs[0], (int) objs[1], (int) objs[2], (int) objs[3]);
                    break;
                default:
                    detach();
                    throw new UnsupportedOperationException("can only convert int arrays of length 2, 3, or 4");
            }
        } else {
            detach();
            throw new UnsupportedOperationException("cannot perform automatic type conversion on array of type " + type.getSimpleName());
        }
        detach();
    }

    public int getId() {
        return handle;
    }

    public static ProgramBuilder builder() {
        return new ProgramBuilder();
    }

    public void destroy() {
        glDeleteProgram(handle);
        attachDepth = 0;
    }

    public void attach() {
        attachDepth++;
        if (attachDepth > 1) return;
        lastShader = glGetInteger(GL_CURRENT_PROGRAM);
        glUseProgram(handle);
    }

    public void detach() {
        attachDepth--;
        if (attachDepth > 0) {
            return;
        }

        if (attachDepth < 0)
            throw new OpenGLException("shader not attached");

        glUseProgram(lastShader);
    }

    public boolean isAttached() {
        return attachDepth > 0;
    }

    public boolean hasUniform(String id) {
        return getUniformLocation(id) >= 0;
    }
}
