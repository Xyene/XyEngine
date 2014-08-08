package tk.ivybits.engine.gl.scene;

import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.scene.model.*;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;

import java.util.*;

import static org.lwjgl.opengl.GL11.*;

public class GeometryDrawable implements IDrawable {
    private final Model model;
    private final int priority;
    private final IDrawContext ctx;
    private List<BufferedMesh> meshes;
    private boolean transparent;

    public GeometryDrawable(IDrawContext with, Model model, int priority) {
        this.ctx = with;
        this.model = model;
        this.priority = priority;
    }

    @Override
    public boolean isTransparent() {
        compile();
        return transparent;
    }

    @Override
    public void draw(IScene scene) {
        compile();

        glPushAttrib(GL_CURRENT_BIT | GL_TEXTURE_BIT);

        glClearColor(0, 0, 0, 0);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DITHER);
        glEnable(GL_BLEND);
        glDisable(GL_COLOR_MATERIAL);
        glColor4f(0, 0, 0, 0);
       // glAlphaFunc(GL_GREATER, 0.0f);

        // Enables/disables
        glEnable(GL_ALPHA_TEST);

        for (BufferedMesh mesh : meshes) {
            if (mesh.material != null) ctx.useMaterial(mesh.material);
            mesh.buffer.draw(scene);
        }

        glPopAttrib();
    }

    private void compile() {
        if (meshes != null) return;
        meshes = new LinkedList<>();

        for (Mesh mesh : model.getMeshes()) {
            Material material = mesh.getMaterial();
            List<Face> faces = mesh.getFaces();

            boolean normals = mesh.hasNormals(), uvs = mesh.hasUVs(), tangents = mesh.hasTangents();

            transparent = transparent || material.transparency != 0;

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

            for (int f = 0; f < faces.size(); f++) {
                Face _face = faces.get(f);
                Vertex[] vertices = _face.getVertices();

                vertex(vertices[0], buffer, normals, tangents, uvs);
                vertex(vertices[1], buffer, normals, tangents, uvs);
                vertex(vertices[2], buffer, normals, tangents, uvs);
                if (_face.getVertexCount() == 4) {
                    vertex(vertices[0], buffer, normals, tangents, uvs);
                    vertex(vertices[2], buffer, normals, tangents, uvs);
                    vertex(vertices[3], buffer, normals, tangents, uvs);
                }
            }

            BufferedMesh bmesh = new BufferedMesh();
            bmesh.buffer = buffer.create();
            bmesh.material = material;
            meshes.add(bmesh);
        }
    }

    public Model getModel() {
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
        return priority;
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