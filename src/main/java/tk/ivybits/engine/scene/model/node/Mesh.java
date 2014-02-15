package tk.ivybits.engine.scene.model.node;

import tk.ivybits.engine.scene.model.IGeometry;

import java.util.ArrayList;
import java.util.List;

public class Mesh implements IGeometry {
    private List<Face> faces;

    public Mesh() {
        this(new ArrayList<Face>());
    }

    public Mesh(List<Face> faces) {
        this.faces = faces;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }
}
