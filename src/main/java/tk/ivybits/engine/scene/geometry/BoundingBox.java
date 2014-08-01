package tk.ivybits.engine.scene.geometry;

import tk.ivybits.engine.scene.model.Face;
import tk.ivybits.engine.scene.model.Mesh;
import tk.ivybits.engine.scene.model.Model;
import tk.ivybits.engine.scene.model.Vertex;
import tk.ivybits.engine.util.ToString;

import javax.vecmath.Vector3f;
import java.util.List;

import static tk.ivybits.engine.util.ToString.Printable;

public class BoundingBox {
    private final @Printable float length;
    private final @Printable float width;
    private final @Printable float height;

    public BoundingBox(float length, float width, float height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    /**
     * Naively computes the bounding box of an {@link tk.ivybits.engine.scene.model.Mesh} instance.
     *
     * @param geom The mesh.
     * @return A bounding box of the given mesh.
     */
    public static BoundingBox getBoundingBox(Model geom) {
        float min_length = 0, min_width = 0, min_height = 0, max_length = 0, max_width = 0, max_height = 0;

        for (Mesh mesh : geom.getMeshes()) {
            List<Face> faces = mesh.getFaces();
            for (Face f : faces) {
                for (Vertex v : f) {
                    Vector3f n = v.pos;
                    max_length = Math.max(max_length, n.x);
                    max_width = Math.max(max_width, n.z);
                    max_height = Math.max(max_height, n.y);
                    min_length = Math.min(min_length, n.x);
                    min_width = Math.min(min_width, n.z);
                    min_height = Math.min(min_height, n.y);
                }
            }
        }
        return new BoundingBox(Math.abs(max_length - min_length), Math.abs(max_width - min_width), Math.abs(max_height - min_height));
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
