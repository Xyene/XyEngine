package tk.ivybits.engine.scene.model;

import tk.ivybits.engine.scene.model.node.Face;
import tk.ivybits.engine.scene.model.node.Vertex;

import javax.vecmath.Vector3f;

public class TangentSpace {
    public static void calculateTangents(IGeometry geometry) {
        for (Face face : geometry.getFaces()) {
            if (face.getVertexCount() != 3)
                throw new UnsupportedOperationException("only tangents for triangle faces can be calculated right now");

            Vertex[] vertices = face.getVertices();

            Vertex v0 = vertices[0];
            Vertex v1 = vertices[1];
            Vertex v2 = vertices[2];

            Vector3f edge1 = new Vector3f(v1.pos);
            edge1.sub(v0.pos);
            Vector3f edge2 = new Vector3f(v2.pos);
            edge2.sub(v0.pos);

            float deltaU1 = v1.uv.x - v0.uv.x;
            float deltaV1 = v1.uv.y - v0.uv.y;
            float deltaU2 = v2.uv.x - v0.uv.x;
            float deltaV2 = v2.uv.y - v0.uv.y;

            float f = 1.0f / (deltaU1 * deltaV2 - deltaU2 * deltaV1);

            Vector3f tangent = new Vector3f(
                    f * (deltaV2 * edge1.x - deltaV1 * edge2.x),
                    f * (deltaV2 * edge1.y - deltaV1 * edge2.y),
                    f * (deltaV2 * edge1.z - deltaV1 * edge2.z));

            if (v0.tangent != null) v0.tangent.add(tangent);
            else v0.tangent = tangent;
            if (v1.tangent != null) v1.tangent.add(tangent);
            else v1.tangent = tangent;
            if (v2.tangent != null) v2.tangent.add(tangent);
            else v2.tangent = tangent;
        }

        for (Face face : geometry.getFaces()) {
            for (Vertex v : face.getVertices()) {
                v.tangent.normalize();
            }
        }
    }
}
