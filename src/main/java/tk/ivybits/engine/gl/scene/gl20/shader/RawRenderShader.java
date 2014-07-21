package tk.ivybits.engine.gl.scene.gl20.shader;

import tk.ivybits.engine.gl.scene.gl20.shader.AbstractShader;
import tk.ivybits.engine.gl.scene.gl20.shader.ISceneShader;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.model.node.Material;

public class RawRenderShader extends AbstractShader implements ISceneShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/raw_render.v.glsl";
    private Program shader;
    private boolean uniformsFetched = false;
    private int
            PROJECTION_MATRIX,
            MODEL_MATRIX,
            VIEW_MATRIX;
    private int[] ATTRIBUTES;

    private void setupHandles() {
        if (uniformsFetched) {
            return;
        }
        boolean attached = isAttached;
        if (!attached) super.attach();
        uniformsFetched = true;
        PROJECTION_MATRIX = shader.getUniformLocation("u_projectionMatrix");
        MODEL_MATRIX = shader.getUniformLocation("u_modelMatrix");
        VIEW_MATRIX = shader.getUniformLocation("u_viewMatrix");

        ATTRIBUTES = new int[]{
                shader.getAttributeLocation("a_Vertex"),
                -1, // No normals
                -1, // No UV
                -1 // No tangents
        };
        if (!attached) super.detach();
    }

    @Override
    public void attach() {
        super.attach();
        setupHandles();
    }

    @Override
    public void useMaterial(Material material) {

    }

    @Override
    protected int getShaderHandle() {
        try {
            // glEnable(GL_RASTERIZER_DISCARD);
            return (shader == null ? shader = Program.builder()
                    .loadSystemShader(Program.ShaderType.VERTEX, VERTEX_SHADER_LOCATION)
                    .build() : shader).getId();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        return ATTRIBUTES[attribute.ordinal()];
    }


    @Override
    public void setProjection(Projection proj) {
        boolean attached = isAttached;
        if (!attached) super.attach();
        setupHandles();
        shader.setUniform(PROJECTION_MATRIX, proj.getProjectionMatrix());
        shader.setUniform(MODEL_MATRIX, proj.getModelMatrix());
        shader.setUniform(VIEW_MATRIX, proj.getViewMatrix());
        if (!attached) super.detach();
    }

    @Override
    public Program getProgram() {
        return shader;
    }
}
