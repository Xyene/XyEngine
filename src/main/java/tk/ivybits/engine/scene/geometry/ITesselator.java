package tk.ivybits.engine.scene.geometry;

import tk.ivybits.engine.scene.IDrawable;

public interface ITesselator {
    int VERTEX_ATTR = 0x1,
            NORMAL_ATTR = 0x2,
            UV_ATTR = 0x4,
            TANGENT_ATTR = 0x8;

    int getType();

    void vertex(float x, float y, float z);

    void normal(float x, float y, float z);

    void texture(float u, float v);

    void tangent(float x, float y, float z);

    IDrawable create();
}
