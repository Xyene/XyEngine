package tk.ivybits.engine.gl.scene.gl20.shader;

import tk.ivybits.engine.gl.Program;
import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;
import tk.ivybits.engine.scene.model.node.Material;

public interface ISceneShader {
    void attach();

    void detach();

    void destroy();

    void useMaterial(Material material);

    int getAttributeLocation(VertexAttribute attribute);

    void setProjection(Projection proj);

    Program getProgram();
}
