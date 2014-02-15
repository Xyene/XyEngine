package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.gl.shader.IShader;

public interface IActor {
    void position(float x, float y, float z);

    float x();

    float y();

    float z();

    float pitch();

    float yaw();

    float roll();

    IGeometry getGeometry();

    void setShader(IShader shader);

    void rotate(float pitch, float yaw, float roll);
}
