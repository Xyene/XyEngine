package tk.ivybits.engine.gl.scene.gl20.shadow;

import tk.ivybits.engine.gl.shader.AbstractShader;
import tk.ivybits.engine.gl.shader.ISceneShader;
import tk.ivybits.engine.gl.shader.Program;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.model.node.Material;

import static org.lwjgl.opengl.GL20.*;

public class ShadowMapShader extends AbstractShader implements ISceneShader {
    private static final String FRAGMENT_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/shadowmap_fbo.f.glsl";
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/shadowmap_fbo.v.glsl";
    private Program shader;

    @Override
    public void useMaterial(Material material) {

    }

    @Override
    public int getShaderHandle() {
        try {
            return (shader == null ? shader = Program.builder()
                    .loadSystemShader(Program.ShaderType.FRAGMENT, FRAGMENT_SHADER_LOCATION)
                    .loadSystemShader(Program.ShaderType.VERTEX, VERTEX_SHADER_LOCATION)
                    .build() : shader).getId();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        String name;
        switch (attribute) {
            case VERTEX_BUFFER:
                name = "a_Vertex";
                break;
            case NORMAL_BUFFER:
                name = "a_Normal";
                break;
            case UV_BUFFER:
                name = "a_UV";
                break;
            case TANGENT_BUFFER:
                name = "a_Tangent";
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return glGetAttribLocation(getShaderHandle(), name);
    }

    @Override
    public void setProjection(Projection proj) {
        throw new RuntimeException();
    }
}
