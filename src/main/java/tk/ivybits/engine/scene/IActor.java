package tk.ivybits.engine.scene;

import org.lwjgl.util.vector.Matrix4f;

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

    Matrix4f getModelMatrix();

    IDrawable createDrawable(IDrawContext context);

    org.lwjgl.util.vector.Vector3f position();

    void rotate(float pitch, float yaw, float roll);
}
