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

package tk.ivybits.engine.gl;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.vector.*;
import tk.ivybits.engine.gl.texture.Texture;

import java.awt.*;
import java.lang.ref.WeakReference;
import java.nio.FloatBuffer;
import java.util.*;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.GL.glUniformMatrix2;

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

    public void setUniform(String handle, Matrix2f matrix) {
        attach();
        matrixBuffer.clear();
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix2(getUniformLocation(handle), false, matrixBuffer);
        detach();
    }

    public void setUniform(String handle, Matrix3f matrix) {
        attach();
        matrixBuffer.clear();
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix3(getUniformLocation(handle), false, matrixBuffer);
        detach();
    }

    public void setUniform(String handle, Matrix4f matrix) {
        attach();
        matrixBuffer.clear();
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4(getUniformLocation(handle), false, matrixBuffer);
        detach();
    }

    public void setUniform(String handle, Vector2f vec) {
        attach();
        glUniform2f(getUniformLocation(handle), vec.x, vec.y);
        detach();
    }

    public void setUniform(String handle, Vector3f vec) {
        attach();
        glUniform3f(getUniformLocation(handle), vec.x, vec.y, vec.z);
        detach();
    }

    public void setUniform(String handle, Vector4f vec) {
        attach();
        glUniform4f(getUniformLocation(handle), vec.x, vec.y, vec.z, vec.w);
        detach();
    }

    public void setUniform(String handle, Number n) {
        if (n instanceof Float)
            setUniform(handle, (float) n);
        else if (n instanceof Double)
            setUniform(handle, (double) n);
        else if (n instanceof Integer)
            setUniform(handle, (int) n);
        else if (n instanceof Short)
            setUniform(handle, (short) n);
        else
            throw new UnsupportedOperationException();
    }

    public void setUniform(String handle, boolean b) {
        attach();
        glUniform1i(getUniformLocation(handle), b ? 1 : 0);
        detach();
    }

    public void setUniform(String handle, int i) {
        attach();
        glUniform1i(getUniformLocation(handle), i);
        detach();
    }

    public void setUniform(String handle, float f) {
        attach();
        glUniform1f(getUniformLocation(handle), f);
        detach();
    }

    public void setUniform(String handle, double d) {
        attach();
        glUniform1d(getUniformLocation(handle), d);
        detach();
    }

    public void setUniform(String handle, Texture texture) {
        attach();
        glUniform1i(getUniformLocation(handle), texture.id());
        detach();
    }

    public void setUniform(String handle, int... ints) {
        attach();
        int id = getUniformLocation(handle);
        switch (ints.length) {
            case 1:
                glUniform1i(id, ints[0]);
                break;
            case 2:
                glUniform2i(id, ints[0], ints[1]);
                break;
            case 3:
                glUniform3i(id, ints[0], ints[1], ints[2]);
                break;
            case 4:
                glUniform4i(id, ints[0], ints[1], ints[2], ints[3]);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        detach();
    }

    public void setUniform(String handle, float... floats) {
        attach();
        int id = getUniformLocation(handle);
        switch (floats.length) {
            case 1:
                glUniform1f(id, floats[0]);
                break;
            case 2:
                glUniform2f(id, floats[0], floats[1]);
                break;
            case 3:
                glUniform3f(id, floats[0], floats[1], floats[2]);
                break;
            case 4:
                glUniform4f(id, floats[0], floats[1], floats[2], floats[3]);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        detach();
    }

    public void setUniform(String handle, double... doubles) {
        attach();
        int id = getUniformLocation(handle);
        switch (doubles.length) {
            case 1:
                glUniform1d(id, doubles[0]);
                break;
            case 2:
                glUniform2d(id, doubles[0], doubles[1]);
                break;
            case 3:
                glUniform3d(id, doubles[0], doubles[1], doubles[2]);
                break;
            case 4:
                glUniform4d(id, doubles[0], doubles[1], doubles[2], doubles[3]);
                break;
            default:
                throw new UnsupportedOperationException();
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
