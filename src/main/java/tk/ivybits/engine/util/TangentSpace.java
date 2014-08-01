package tk.ivybits.engine.util;

import org.lwjgl.util.vector.Vector3f;
import tk.ivybits.engine.scene.model.Face;
import tk.ivybits.engine.scene.model.Vertex;

public class TangentSpace {
    private static void calculate(Vertex[] vertices, int i, int j, int k) {
        Vertex v0 = vertices[i];
        Vertex v1 = vertices[j];
        Vertex v2 = vertices[k];

        Vector3f edge1 = Vector3f.sub(v1.pos, v0.pos, null);
        Vector3f edge2 = Vector3f.sub(v2.pos, v0.pos, null);

        float deltaU1 = v1.uv.x - v0.uv.x;
        float deltaV1 = v1.uv.y - v0.uv.y;
        float deltaU2 = v2.uv.x - v0.uv.x;
        float deltaV2 = v2.uv.y - v0.uv.y;

        float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

        Vector3f tangent = new Vector3f(
                f * (deltaV2 * edge1.x - deltaV1 * edge2.x),
                f * (deltaV2 * edge1.y - deltaV1 * edge2.y),
                f * (deltaV2 * edge1.z - deltaV1 * edge2.z));

        if (v0.tangent != null) Vector3f.add(v0.tangent, tangent, v0.tangent);
        else v0.tangent = tangent;
        if (v1.tangent != null) Vector3f.add(v1.tangent, tangent, v1.tangent);
        else v1.tangent = tangent;
        if (v2.tangent != null) Vector3f.add(v2.tangent, tangent, v2.tangent);
        else v2.tangent = tangent;
    }

    public static void calculateTangents(Face face) {
        if (face.getVertexCount() != 3 && face.getVertexCount() != 4)
            throw new UnsupportedOperationException("only tangents for triangle & quad faces can be calculated right now");

        Vertex[] vertices = face.getVertices();
        calculate(vertices, 0, 1, 2);
        if (face.getVertexCount() == 4)
            calculate(vertices, 0, 2, 3);

        for (Vertex v : face.getVertices()) {
            v.tangent.normalise(v.tangent);
        }
    }
}
