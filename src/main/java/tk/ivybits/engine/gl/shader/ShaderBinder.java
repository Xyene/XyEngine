package tk.ivybits.engine.gl.shader;

import java.io.*;

import static org.lwjgl.opengl.ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;

public class ShaderBinder {

    private static String readSourceFrom(InputStream in) throws IOException {
        StringBuilder source = new StringBuilder();
        BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
            String line;
            while ((line = reader.readLine()) != null) {
                source.append(line).append('\n');
            }
        return source.toString();
    }

    /**
     * Loads a shader program from two source files.
     *
     * @param vertexShaderLocation   the location of the file containing the vertex shader source
     * @param fragmentShaderLocation the location of the file containing the fragment shader source
     * @return the shader program or -1 if the loading or compiling failed
     */
    public static int loadShaderPair(InputStream vertexShaderLocation, InputStream fragmentShaderLocation) throws IOException {
        int shaderProgram = glCreateProgramObjectARB();
        int vertexShader = glCreateShaderObjectARB(GL_VERTEX_SHADER_ARB);
        int fragmentShader = glCreateShaderObjectARB(GL_FRAGMENT_SHADER_ARB);
        String vertexShaderSource = readSourceFrom(vertexShaderLocation);
        String fragmentShaderSource = readSourceFrom(fragmentShaderLocation);

        glShaderSourceARB(vertexShader, vertexShaderSource);
        glCompileShaderARB(vertexShader);
        if (glGetObjectParameteriARB(vertexShader, GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
            System.err.println("Vertex shader wasn't able to be compiled correctly. Error log:");
            System.err.println(glGetInfoLogARB(vertexShader, 1024));
            return -1;
        }
        glShaderSourceARB(fragmentShader, fragmentShaderSource);
        glCompileShaderARB(fragmentShader);
        if (glGetObjectParameteriARB(fragmentShader, GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
            System.err.println("Fragment shader wasn't able to be compiled correctly. Error log:");
            System.err.println(glGetInfoLogARB(fragmentShader, 1024));
        }
        glAttachObjectARB(shaderProgram, vertexShader);
        glAttachObjectARB(shaderProgram, fragmentShader);

        glBindAttribLocation(shaderProgram, 5, "tangent");
        glLinkProgramARB(shaderProgram);
        if (glGetObjectParameteriARB(shaderProgram, GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
            System.err.println("Shader program wasn't linked correctly.");
            System.err.println(glGetInfoLogARB(shaderProgram, 1024));
            return -1;
        }
        glDeleteObjectARB(vertexShader);
        glDeleteObjectARB(fragmentShader);
        return shaderProgram;
    }
}
