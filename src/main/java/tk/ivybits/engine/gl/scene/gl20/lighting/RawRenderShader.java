package tk.ivybits.engine.gl.scene.gl20.lighting;

import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.util.IO;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RawRenderShader extends BaseShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/raw_render.v.glsl";
    private static final String VERTEX_SHADER_SOURCE;

    static {
        try {
            VERTEX_SHADER_SOURCE = IO.readString(ClassLoader.getSystemResourceAsStream(VERTEX_SHADER_LOCATION));
        } catch (IOException e) {
            throw new OpenGLException(e);
        }
    }

    public RawRenderShader(IScene scene, HashMap<FrameBuffer, Matrix4f> shadowMapFBO) {
        super(new HashMap<ProgramType, List<String>>() {{
            put(ProgramType.VERTEX_SHADER, Arrays.asList(VERTEX_SHADER_SOURCE));
        }}, scene, shadowMapFBO);
    }
}
