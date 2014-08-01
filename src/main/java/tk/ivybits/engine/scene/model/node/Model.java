package tk.ivybits.engine.scene.model.node;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<Mesh> meshes;

    public Model() {
        this(new ArrayList<Mesh>());
    }

    public Model(List<Mesh> faces) {
        this.meshes = faces;
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public void setMeshes(List<Mesh> faces) {
        this.meshes = faces;
    }
}
