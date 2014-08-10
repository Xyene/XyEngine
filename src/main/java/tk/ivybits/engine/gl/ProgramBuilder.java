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

import org.lwjgl.opengl.OpenGLException;
import tk.ivybits.engine.io.IO;

import java.io.*;
import java.util.*;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.GL.glShaderSource;

public class ProgramBuilder {
    private static final int[] SHADER_ENUM_LOOKUP = {
            GL_FRAGMENT_SHADER,
            GL_VERTEX_SHADER,
            GL_GEOMETRY_SHADER,
            GL_TESS_EVALUATION_SHADER,
            GL_TESS_CONTROL_SHADER
    };
    private HashMap<ProgramType, List<String>> shaders = new HashMap<>();
    private HashMap<String, String> defines = new HashMap<>();

    public ProgramBuilder loadShader(ProgramType type, String path) throws FileNotFoundException {
        return loadShader(type, new FileInputStream(new File(path)));
    }

    public ProgramBuilder loadPackagedShader(ProgramType type, String path) {
        return loadShader(type, ClassLoader.getSystemResourceAsStream(path));
    }

    public ProgramBuilder loadShader(ProgramType type, InputStream in) {
        try {
            return addShader(type, IO.readString(in));
        } catch (IOException e) {
            throw new OpenGLException(e.getMessage());
        }
    }

    public ProgramBuilder addShader(ProgramType type, String source) {
        List<String> sources = shaders.get(type);
        if (sources == null) {
            sources = new ArrayList<>();
            shaders.put(type, sources);
        }
        sources.add(source);
        return this;
    }

    public Program build() {
        int handle = glCreateProgram();
        List<Integer> toUnbind = new ArrayList<>(shaders.size());
        for (Map.Entry<ProgramType, List<String>> shader : shaders.entrySet()) {
            int shaderHandle = glCreateShader(SHADER_ENUM_LOOKUP[shader.getKey().ordinal()]);

            String header = "";
            for (Map.Entry<String, String> def : defines.entrySet()) {
                header += "#define " + def.getKey() + " " + def.getValue() + "\n";
            }

            List<String> value = shader.getValue();
            String[] sources = new String[value.size()];
            for (int i = 0; i < value.size(); i++) {
                String source = value.get(i);
                if (source.startsWith("#version")) {
                    int idx = source.indexOf('\n');
                    source = source.substring(0, idx + 1) + header + source.substring(idx + 1);
                } else {
                    source = header + source;
                }
                sources[i] = source;
            }

            glShaderSource(shaderHandle, sources);

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
            final String log = glGetShaderInfoLog(handle, glGetShaderi(handle, GL_INFO_LOG_LENGTH));
            System.err.println(log);

            glDeleteProgram(handle);
            handle = -1;
        }

        for (int shader : toUnbind) {
            glDetachShader(handle, shader);
        }
        return new Program(handle);
    }

    public ProgramBuilder define(String key, String value) {
        defines.put(key, value);
        return this;
    }

    public ProgramBuilder define(String key) {
        return define(key, "");
    }

    public ProgramBuilder add(Map<ProgramType, List<String>> sources) {
        for (Map.Entry<ProgramType, List<String>> source : sources.entrySet()) {
            for (String part : source.getValue()) {
                addShader(source.getKey(), part);
            }
        }
        return this;
    }
}
