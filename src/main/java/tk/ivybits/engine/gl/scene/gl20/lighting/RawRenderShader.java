package tk.ivybits.engine.gl.scene.gl20.lighting;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.ProgramBuilder;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.scene.gl20.shader.ISceneShader;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.model.node.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawRenderShader extends BaseShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/raw_render.v.glsl";
    private static final String VERTEX_SHADER_SOURCE;

    static {
        VERTEX_SHADER_SOURCE = ProgramBuilder.readSourceFrom(ClassLoader.getSystemResourceAsStream(VERTEX_SHADER_LOCATION));
    }

    public RawRenderShader(IScene scene, HashMap<FrameBuffer, Matrix4f> shadowMapFBO) {
        super(new HashMap<ProgramType, List<String>>() {{
            put(ProgramType.VERTEX_SHADER, Arrays.asList(VERTEX_SHADER_SOURCE));
        }}, scene, shadowMapFBO);
    }
}
