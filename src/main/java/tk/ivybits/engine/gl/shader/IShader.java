package tk.ivybits.engine.gl.shader;

import tk.ivybits.engine.scene.model.node.Material;

public interface IShader {
    void attach();

    void detach();

    void destroy();

    void useMaterial(Material material);
}
