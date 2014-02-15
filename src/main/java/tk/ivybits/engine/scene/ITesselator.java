package tk.ivybits.engine.scene;

import java.nio.FloatBuffer;

public interface ITesselator {

    ITesselator update(FloatBuffer buffer);

    ITesselator withOffset(VertexAttribute attr, int stride, int offset);

    void withIndiceCount(int indiceCount);

    ITesselator flush();

    ITesselator draw(int type);
}
