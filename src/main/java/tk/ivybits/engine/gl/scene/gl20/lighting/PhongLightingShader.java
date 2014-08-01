package tk.ivybits.engine.gl.scene.gl20.lighting;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.ProgramBuilder;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.texture.FrameBuffer;
import tk.ivybits.engine.scene.IScene;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PhongLightingShader extends BaseShader {
    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/phong_lighting.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/phong_lighting.v.glsl";
    private static final String BASE_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/lighting.glsl";
    private static final String VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE, BASE_SHADER_SOURCE;

    static {
        VERTEX_SHADER_SOURCE = ProgramBuilder.readSourceFrom(ClassLoader.getSystemResourceAsStream(VERTEX_SHADER_LOCATION));
        FRAGMENT_SHADER_SOURCE = ProgramBuilder.readSourceFrom(ClassLoader.getSystemResourceAsStream(FRAGMENT_SHADER_LOCATION));
        BASE_SHADER_SOURCE = ProgramBuilder.readSourceFrom(ClassLoader.getSystemResourceAsStream(BASE_SHADER_LOCATION));
    }

    public PhongLightingShader(IScene scene, HashMap<FrameBuffer, Matrix4f> shadowMapFBO) {
        super(new HashMap<ProgramType, List<String>>() {{
            put(ProgramType.VERTEX_SHADER, Arrays.asList(VERTEX_SHADER_SOURCE));
            put(ProgramType.FRAGMENT_SHADER, Arrays.asList(BASE_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE));
        }}, scene, shadowMapFBO);
    }
}
