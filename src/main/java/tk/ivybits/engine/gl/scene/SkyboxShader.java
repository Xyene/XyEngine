package tk.ivybits.engine.gl.scene;

import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.scene.gl20.shader.BaseShader;
import tk.ivybits.engine.gl.texture.Framebuffer;
import tk.ivybits.engine.io.IO;
import tk.ivybits.engine.scene.IScene;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class SkyboxShader extends BaseShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/skybox/skybox.v.glsl";
    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/skybox/skybox.f.glsl";
    private static final String VERTEX_SHADER_SOURCE;
    private static final String FRAGMENT_SHADER_SOURCE;

    static {
        try {
            VERTEX_SHADER_SOURCE = IO.readString(ClassLoader.getSystemResourceAsStream(VERTEX_SHADER_LOCATION));
            FRAGMENT_SHADER_SOURCE = IO.readString(ClassLoader.getSystemResourceAsStream(FRAGMENT_SHADER_LOCATION));
        } catch (IOException e) {
            throw new OpenGLException(e);
        }
    }

    public SkyboxShader(IScene scene) {
        super(new HashMap<ProgramType, List<String>>() {{
            put(ProgramType.VERTEX_SHADER, Arrays.asList(VERTEX_SHADER_SOURCE));
            put(ProgramType.FRAGMENT_SHADER, Arrays.asList(FRAGMENT_SHADER_SOURCE));
        }}, scene, new HashMap<Framebuffer, Matrix4f>());
    }
}
