package tk.ivybits.engine.gl.shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static tk.ivybits.engine.gl.GL.*;

public class Program {
    public enum ShaderType {
        FRAGMENT, VERTEX // TODO: geometry & tesselation
    }

    private static final int[] SHADER_LOOKUP = {GL_FRAGMENT_SHADER, GL_VERTEX_SHADER};
    private final int handle;

    public Program(int handle) {
        this.handle = handle;
    }

    public int getUniformLocation(String name) {
        int id = glGetUniformLocation(handle, name);
        return id;
    }

    private Class ensureConstantTypes(Object[] objs) {
        Class c = objs[0].getClass();
        for (int n = 1; n < objs.length; n++)
            if (objs[n].getClass() != c)
                throw new IllegalArgumentException("values not of constant type");
        return c;
    }

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public void setUniform(int handle, Object... objs) {
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

    public int getAttributeLocation(String name) {
        int id = glGetAttribLocation(handle, name);
        return id;
    }

    public int getId() {
        return handle;
    }

    public static class ProgramBuilder {
        private List<Map.Entry<ShaderType, String>> shaders = new ArrayList<>();

        private static String readSourceFrom(InputStream in) {
            StringBuilder source = new StringBuilder();
            BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    source.append(line).append('\n');
                }
            } catch (IOException e) {
                throw new IllegalStateException(e); // TODO: better?
            }
            return source.toString();
        }

        public ProgramBuilder loadShader(ShaderType type, String path) throws FileNotFoundException {
            return loadShader(type, new FileInputStream(new File(path)));
        }

        public ProgramBuilder loadSystemShader(ShaderType type, String path) {
            return loadShader(type, ClassLoader.getSystemResourceAsStream(path));
        }

        public ProgramBuilder loadShader(ShaderType type, InputStream in) {
            return addShader(type, readSourceFrom(in));
        }

        public ProgramBuilder addShader(ShaderType type, String source) {
            shaders.add(new AbstractMap.SimpleImmutableEntry<>(type, source));
            return this;
        }


        public Program build() {
            int handle = glCreateProgram();
            List<Integer> toUnbind = new ArrayList<>(shaders.size());
            for (Map.Entry<ShaderType, String> shader : shaders) {
                int shaderHandle = glCreateShader(SHADER_LOOKUP[shader.getKey().ordinal()]);

                glShaderSource(shaderHandle, shader.getValue());
                glCompileShader(shaderHandle);
                final String log = glGetShaderInfoLog(shaderHandle, glGetShaderi(shaderHandle, GL_INFO_LOG_LENGTH));
                System.err.println(log);
                if (glGetShaderi(shaderHandle, GL_COMPILE_STATUS) == GL_FALSE) {
                    glDeleteShader(shaderHandle);
                }
                toUnbind.add(shaderHandle);
            }
            for (int shader : toUnbind) {
                glAttachShader(handle, shader);
            }
            glLinkProgram(handle);
            if (glGetProgrami(handle, GL_LINK_STATUS) == GL_FALSE) {
                final String log = glGetProgramInfoLog(handle, glGetProgrami(handle, GL_INFO_LOG_LENGTH));

                System.err.println(log);

                glDeleteProgram(handle);
                handle = -1;
            }

            for (int shader : toUnbind) {
                glDetachShader(handle, shader);
            }
            return new Program(handle);
        }
    }

    public static ProgramBuilder builder() {
        return new ProgramBuilder();
    }
}
