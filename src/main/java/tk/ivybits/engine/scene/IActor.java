package tk.ivybits.engine.scene;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.gl.shader.IShader;

import javax.vecmath.Vector3f;

public interface IActor  {
    void update(float delta);

    void position(float x, float y, float z);

    float x();

    float y();

    float z();

    float pitch();

    float yaw();

    float roll();

    IDrawable createDrawable(IDrawContext context);

    Vector3f position();

    void rotate(float pitch, float yaw, float roll);
}
