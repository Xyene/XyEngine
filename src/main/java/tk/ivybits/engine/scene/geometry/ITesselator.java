package tk.ivybits.engine.scene.geometry;

import tk.ivybits.engine.scene.IDrawable;

public interface ITesselator {
    int NORMAL_ATTR = 0x1,
            UV_ATTR = 0x2,
            TANGENT_ATTR = 0x4;

    int getType();

    void vertex(float x, float y, float z);

    void normal(float x, float y, float z);

    void texture(float u, float v);

    void tangent(float x, float y, float z);

    IDrawable create();
}
