package tk.ivybits.engine.gl.shader;

import tk.ivybits.engine.scene.VertexAttribute;
import tk.ivybits.engine.scene.camera.Projection;

public interface ISceneShader extends IShader {
    int getAttributeLocation(VertexAttribute attribute);

    void setProjection(Projection proj);
}
