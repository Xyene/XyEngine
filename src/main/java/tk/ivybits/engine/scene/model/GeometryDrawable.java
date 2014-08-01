package tk.ivybits.engine.scene.model;

import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.node.Face;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.model.node.Vertex;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class GeometryDrawable implements IDrawable {
    private final IGeometry model;
    private final IDrawContext ctx;
    private List<BufferedMesh> meshes;
    private boolean transparent;

    public GeometryDrawable(IDrawContext with, IGeometry model) {
        this.ctx = with;
        this.model = model;
    }

    @Override
    public boolean isTransparent() {
        if (meshes == null) {
            meshes = compile();
        }
        return transparent;
    }

    @Override
    public void draw(IScene scene) {
        if (meshes == null) {
            meshes = compile();
        }

        glPushAttrib(GL_CURRENT_BIT | GL_ENABLE_BIT | GL_TEXTURE_BIT);

        for (BufferedMesh mesh : meshes) {
            if (mesh.material != null) ctx.useMaterial(mesh.material);
            mesh.buffer.draw(scene);
        }

        glPopAttrib();
    }

    private List<BufferedMesh> compile() {
        List<BufferedMesh> buffered = new ArrayList<>();

        List<Face> faces = model.getFaces();
        if (faces.size() == 0) return Collections.emptyList();

        Collections.sort(faces, new Comparator<Face>() {
            @Override
            public int compare(Face o1, Face o2) {
                return o1.getMaterial().hashCode() - o2.getMaterial().hashCode();
            }
        });

        Material currentMaterial = faces.get(0).getMaterial();

        int position = 0, limit;
        boolean normals = false, uvs = false, tangents = false;

        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.get(i);
            for (Vertex v : face.getVertices()) {
                normals = normals || v.normal != null;
                tangents = tangents || v.tangent != null;
                uvs = uvs || v.uv != null;
            }

            Material mat = face.getMaterial();
            transparent = transparent || mat.transparency != 0;

            if (mat != currentMaterial || i == faces.size() - 1) { // Check last index: some models have no texture and only one material
                limit = i + 1;

                int flag = 0;
                if (normals) {
                    flag |= ITesselator.NORMAL_ATTR;
                }
                if (uvs) {
                    flag |= ITesselator.UV_ATTR;
                }
                if (tangents) {
                    flag |= ITesselator.TANGENT_ATTR;
                }
                ITesselator buffer = ctx.createTesselator(flag, GL_TRIANGLES);

                for (int f = position; f < limit; f++) {
                    Face _face = faces.get(f);
                    Vertex[] vertices = _face.getVertices();

                    vertex(vertices[0], buffer, normals, tangents, uvs);
                    vertex(vertices[1], buffer, normals, tangents, uvs);
                    vertex(vertices[2], buffer, normals, tangents, uvs);
                    if (face.getVertexCount() == 4) {
                        vertex(vertices[0], buffer, normals, tangents, uvs);
                        vertex(vertices[2], buffer, normals, tangents, uvs);
                        vertex(vertices[3], buffer, normals, tangents, uvs);
                    }
                }

                BufferedMesh bmesh = new BufferedMesh();
                bmesh.buffer = buffer.create();


                bmesh.material = currentMaterial;
                buffered.add(bmesh);

                currentMaterial = mat;
                position = limit;
                normals = uvs = tangents = false;
            }
        }

        return buffered;
    }

    public IGeometry getModel() {
        return model;
    }

    @Override
    public void destroy() {
        for (BufferedMesh mesh : meshes)
            mesh.destroy();
        meshes.clear();
    }

    @Override
    public int priority() {
        return 0;
    }

    private static void vertex(Vertex vertex, ITesselator tes, boolean normals, boolean tangents, boolean uvs) {
        tes.vertex(vertex.pos.x, vertex.pos.y, vertex.pos.z);
        if (normals) {
            if (vertex.normal != null) {
                tes.normal(vertex.normal.x, vertex.normal.y, vertex.normal.z);
            } else {
                tes.normal(0, 0, 0);
            }
        }
        if (uvs) {
            if (vertex.uv != null) {
                tes.texture(vertex.uv.x, vertex.uv.y);
            } else {
                tes.texture(0, 0);
            }
        }
        if (tangents) {
            if (vertex.tangent != null) {
                tes.tangent(vertex.tangent.x, vertex.tangent.y, vertex.tangent.z);
            } else {
                tes.tangent(0, 0, 0);
            }
        }
    }

    public static class BufferedMesh {
        public Material material;
        public IDrawable buffer;

        public void destroy() {
            buffer.destroy();
        }
    }
}