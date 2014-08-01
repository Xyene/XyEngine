package tk.ivybits.engine.gl.scene.gl20.lighting;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.gl.ProgramType;
import tk.ivybits.engine.gl.scene.gl20.shader.ISceneShader;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.model.node.Material;

public class RawRenderShader implements ISceneShader {
    private static final String VERTEX_SHADER_LOCATION = "tk/ivybits/engine/gl/shader/raw_render.v.glsl";
    private Program shader;
    private Matrix4f modelMatrix = new Matrix4f(), viewMatrix = new Matrix4f(), projectionMatrix = new Matrix4f();

    @Override
    public void useMaterial(Material material) {

    }

    @Override
    public int getAttributeLocation(VertexAttribute attribute) {
        return new int[]{
                getProgram().getAttributeLocation("a_Vertex"),
                -1, // No normals
                -1, // No UV
                -1  // No tangents
        }[attribute.ordinal()];
    }

    @Override
    public Program getProgram() {
        return shader == null ? shader = Program.builder()
                .loadPackagedShader(ProgramType.VERTEX_SHADER, VERTEX_SHADER_LOCATION)
                .build() : shader;
    }

    private void setProjection() {
        boolean attached = getProgram().isAttached();
        if (!attached) shader.attach();
        shader.setUniform("u_projectionMatrix", projectionMatrix);
        shader.setUniform("u_modelMatrix", modelMatrix);
        shader.setUniform("u_viewMatrix", viewMatrix);
        if (!attached) shader.detach();
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
