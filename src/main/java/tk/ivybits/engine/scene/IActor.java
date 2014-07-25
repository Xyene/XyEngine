package tk.ivybits.engine.scene;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public interface IActor  {
    void update(float delta);

    void position(float x, float y, float z);

    float x();

    float y();

    float z();

    float pitch();

    float yaw();

    float roll();

    BoundingBox getBoundingBox();

    Matrix4f getTransform();

    IDrawable createDrawable(IDrawContext context);

    Vector3f position();

    void rotate(float pitch, float yaw, float roll);
}
