/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

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

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);

        for (BufferedMesh mesh : meshes) {
            if (mesh.material != null) ctx.useMaterial(mesh.material);
            mesh.buffer.draw(scene);
        }

        glPopAttrib();
    }

    private void compile() {
        if (meshes != null) return;
        meshes = new LinkedList<>();

        List<Mesh> modelMeshes = model.getMeshes();
        for (Mesh mesh : modelMeshes) {
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