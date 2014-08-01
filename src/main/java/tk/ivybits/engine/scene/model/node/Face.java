package tk.ivybits.engine.scene.model.node;

import java.util.Arrays;
import java.util.Iterator;

public class Face implements Iterable<Vertex> {
    private final int vertexCount;
    private Vertex[] vertices;

    public Face(int vertexCount) {
        this.vertexCount = vertexCount;
        vertices = new Vertex[vertexCount];
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return Arrays.asList(vertices).iterator();
    }
}
