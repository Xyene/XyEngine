package tk.ivybits.engine.scene.model.node;

import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private final Material material;
    private final List<Face> faces;

    public Mesh(Material material) {
        this(material, new ArrayList<Face>());
    }

    public Mesh(Material material, List<Face> faces) {
        this.material = material;
        this.faces = faces;
    }

    // TODO: though this should exit extremely quickly, perhaps cache result?
    public boolean hasNormals() {
        for (Face face : faces)
            for (Vertex vertex : face)
                if (vertex.normal != null)
                    return true;
        return false;
    }

    public boolean hasUVs() {
        for (Face face : faces)
            for (Vertex vertex : face)
                if (vertex.uv != null)
                    return true;
        return false;
    }

    public boolean hasTangents() {
        for (Face face : faces)
            for (Vertex vertex : face)
                if (vertex.tangent != null)
                    return true;
        return false;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public Material getMaterial() {
        return material;
    }
}
