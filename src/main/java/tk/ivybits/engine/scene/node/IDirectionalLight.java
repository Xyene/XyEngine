package tk.ivybits.engine.scene.node;

public interface IDirectionalLight {
    IDirectionalLight setRotation(float pitch, float yaw);

    float pitch();

    float yaw();

    float dx();

    float dy();

    float dz();
}
