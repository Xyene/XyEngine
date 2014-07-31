package tk.ivybits.engine.gl;

import org.lwjgl.opengl.OpenGLException;

import java.io.*;
import java.util.*;

import static tk.ivybits.engine.gl.GL.*;

public class ProgramBuilder {
    private static final int[] SHADER_ENUM_LOOKUP = {
            GL_FRAGMENT_SHADER,
            GL_VERTEX_SHADER,
            GL_GEOMETRY_SHADER,
            GL_TESS_EVALUATION_SHADER,
            GL_TESS_CONTROL_SHADER
    };
    private List<Map.Entry<ProgramType, String>> shaders = new ArrayList<>();
    private HashMap<String, String> defines = new HashMap<>();

    public static String readSourceFrom(InputStream in) {
        StringBuilder source = new StringBuilder();
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                source.append(line).append('\n');
            }
        } catch (IOException e) {
            throw new OpenGLException(e.getMessage());
        }
        return source.toString();
    }

    public ProgramBuilder loadShader(ProgramType type, String path) throws FileNotFoundException {
        return loadShader(type, new FileInputStream(new File(path)));
    }

    public ProgramBuilder loadPackagedShader(ProgramType type, String path) {
        return loadShader(type, ClassLoader.getSystemResourceAsStream(path));
    }

    public ProgramBuilder loadShader(ProgramType type, InputStream in) {
        return addShader(type, readSourceFrom(in));
    }

    public ProgramBuilder addShader(ProgramType type, String source) {
        shaders.add(new AbstractMap.SimpleImmutableEntry<>(type, source));
        return this;
    }

    public Program build() {
        int handle = glCreateProgram();
        List<Integer> toUnbind = new ArrayList<>(shaders.size());
        for (Map.Entry<ProgramType, String> shader : shaders) {
            int shaderHandle = glCreateShader(SHADER_ENUM_LOOKUP[shader.getKey().ordinal()]);

            String header = "";
            for (Map.Entry<String, String> def : defines.entrySet()) {
                header += "#define " + def.getKey() + " " + def.getValue() + "\n";
            }

            String source = shader.getValue().trim();
            if (source.startsWith("#version")) {
                int idx = source.indexOf('\n');
                source = source.substring(0, idx + 1) + header + source.substring(idx + 1);
            } else {
                source = header + source;
            }

            glShaderSource(shaderHandle, source);
            glCompileShader(shaderHandle);
            String log = glGetShaderInfoLog(shaderHandle, glGetShaderi(shaderHandle, GL_INFO_LOG_LENGTH));
            if (log.length() > 0) System.err.println(log);
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
            String log = glGetProgramInfoLog(handle, glGetProgrami(handle, GL_INFO_LOG_LENGTH));

            if (log.length() > 0) System.err.println(log);

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
}
