package tk.ivybits.engine.gl.shader;

import tk.ivybits.engine.scene.VertexAttribute;

public interface IGeometryShader extends IShader {
    int getAttributeLocation(VertexAttribute attribute);
}
