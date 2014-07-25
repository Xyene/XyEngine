package tk.ivybits.engine.gl.scene.gl20.shader;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.model.node.Material;

public class RawRenderShader extends AbstractShader implements ISceneShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/raw_render.v.glsl";
    private Program shader;
    private Matrix4f modelMatrix = new Matrix4f(), viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();

    @Override
    public void attach() {
        super.attach();
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
        return new int[]{
                shader.getAttributeLocation("a_Vertex"),
                -1, // No normals
                -1, // No UV
                -1 // No tangents
        }[attribute.ordinal()];
    }

    @Override
    public Program getProgram() {
        return shader;
    }

    private void setProjection() {
        boolean attached = isAttached;
        if (!attached) super.attach();
        shader.setUniform(shader.getUniformLocation("u_projectionMatrix"), projectionMatrix);
        shader.setUniform(shader.getUniformLocation("u_modelMatrix"), modelMatrix);
        shader.setUniform(shader.getUniformLocation("u_viewMatrix"), viewMatrix);
        if (!attached) super.detach();
    }

    public void setModelTransform(Matrix4f modelMatrix) {
        this.modelMatrix = modelMatrix;
        setProjection();
    }

    @Override
    public void setViewTransform(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
        setProjection();
    }

    @Override
    public void setProjectionTransform(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
        setProjection();
    }
}
