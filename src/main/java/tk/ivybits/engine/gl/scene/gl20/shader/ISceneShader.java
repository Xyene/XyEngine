package tk.ivybits.engine.gl.scene.gl20.shader;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.model.node.Material;

public interface ISceneShader {
    void useMaterial(Material material);

    int getAttributeLocation(VertexAttribute attribute);

    Program getProgram();

    void setModelTransform(Matrix4f modelMatrix);

    void setViewTransform(Matrix4f viewMatrix);

    void setProjectionTransform(Matrix4f projectionMatrix);
}
