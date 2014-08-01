package tk.ivybits.engine.gl;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import java.nio.FloatBuffer;
import java.util.*;

import static tk.ivybits.engine.gl.GL.*;

public class Program {
    private final int handle;
    private final HashMap<String, Integer> uniforms = new HashMap<>();
    private final HashMap<String, Integer> attributes = new HashMap<>();
    private boolean isAttached;

    public Program(int handle) {
        this.handle = handle;
    }

    public int getUniformLocation(String name) {
        Integer loc = uniforms.get(name);
        if (loc == null) {
            loc = glGetUniformLocation(handle, name);
            uniforms.put(name, loc);
        }
        return loc;
    }

    public int getAttributeLocation(String name) {
        Integer loc = attributes.get(name);
        if (loc == null) {
            loc = glGetAttribLocation(handle, name);
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
                throw new UnsupportedOperationException("cannot perform automatic type conversion on object of type " + obj.getClass().getSimpleName());
            }
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
                    throw new UnsupportedOperationException("can only convert float arrays of length 2, 3, or 4");
            }
        } else {
            throw new UnsupportedOperationException("cannot perform automatic type conversion on array of type " + type.getSimpleName());
        }
    }

    public int getId() {
        return handle;
    }

    public static ProgramBuilder builder() {
        return new ProgramBuilder();
    }

    public void destroy() {
        glDeleteProgram(handle);
        isAttached = false;
    }

    public void attach() {
        glUseProgram(handle);
        isAttached = true;
    }

    public void detach() {
        glUseProgram(0);
        isAttached = false;
    }

    public boolean isAttached() {
        return isAttached;
    }

    public boolean hasUniform(String id) {
        return getUniformLocation(id) >= 0;
    }
}
