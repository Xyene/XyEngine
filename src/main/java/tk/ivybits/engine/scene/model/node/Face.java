package tk.ivybits.engine.scene.model.node;

public class Face {
    private final int vertexCount;
    private Vertex[] vertices;
    private Material material;

    public Face(int vertexCount, Material material) {
        this.vertexCount = vertexCount;
        this.material = material;
        vertices = new Vertex[vertexCount];
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
