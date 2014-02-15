package tk.ivybits.engine.scene.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.scene.model.node.Face;
import tk.ivybits.engine.scene.model.node.Material;
import tk.ivybits.engine.scene.model.node.Vertex;
import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.ITesselator;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static tk.ivybits.engine.scene.VertexAttribute.*;

public class GeometryDrawable implements IDrawable {
    private final IGeometry model;
    private final IDrawContext ctx;
    private List<BufferedMesh> meshes;

    public GeometryDrawable(IDrawContext with, IGeometry model) {
        this.ctx = with;
        if (!GLContext.getCapabilities().OpenGL15)
            throw new IllegalArgumentException("OpenGL 1.5+ is needed for VBOs.");
        this.model = model;
        TangentSpace.calculateTangents(model);
    }

    @Override
    public void draw() {
        if (meshes == null) {
            meshes = compile();
        }

        glPushAttrib(GL_CURRENT_BIT | GL_ENABLE_BIT | GL_TEXTURE_BIT);

        for (int m = 0; m < meshes.size(); m++) {
            BufferedMesh mesh = meshes.get(m);
            ctx.useMaterial(mesh.material);
            mesh.buffer.draw(GL_TRIANGLES);
        }

        glPopAttrib();
    }

    private List<BufferedMesh> compile() {
        List<BufferedMesh> buffered = new ArrayList<>();

        int totalIndexCount = 0;

        List<Face> faces = model.getFaces();
        if (faces.size() == 0) return Collections.emptyList();

        Material currentMaterial = faces.get(0).getMaterial();

        int position = 0, limit = 0;
        boolean normals = false, uvs = false, tangents = false;

        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.get(i);
            for (Vertex v : face.getVertices()) {
                normals = normals || v.normal != null;
                tangents = tangents || v.tangent != null;
                uvs = uvs || v.uv != null;
            }

            Material mat = face.getMaterial();

            totalIndexCount += face.getVertexCount() - 2;

            if (mat != currentMaterial || i == faces.size() - 1) { // Check last index: some models have no texture and only one material
                limit = i + 1;

                int indexSize = 3;
                if (normals) indexSize += 3;
                if (tangents) indexSize += 3;
                if (uvs) indexSize += 2;

                FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(totalIndexCount * indexSize * 3);

                for (int f = position; f < limit; f++) {
                    Face _face = faces.get(f);
                    Vertex[] vertices = _face.getVertices();

                    vertex(vertices[0], vertexBuffer, normals, tangents, uvs);
                    vertex(vertices[1], vertexBuffer, normals, tangents, uvs);
                    vertex(vertices[2], vertexBuffer, normals, tangents, uvs);
                    if (face.getVertexCount() == 4) {
                        vertex(vertices[0], vertexBuffer, normals, tangents, uvs);
                        vertex(vertices[2], vertexBuffer, normals, tangents, uvs);
                        vertex(vertices[3], vertexBuffer, normals, tangents, uvs);
                    }
                }
                vertexBuffer.flip();

                BufferedMesh bmesh = new BufferedMesh();
                ITesselator buffer = ctx.createTesselator();
                bmesh.buffer = buffer;

                buffer.update(vertexBuffer);

                int stride = indexSize * 4;
                buffer.withOffset(VERTEX, stride, 0);
                if (normals) buffer.withOffset(NORMAL, stride, 12);
                if (uvs) buffer.withOffset(UV, stride, normals ? 24 : 12);
                if (tangents) buffer.withOffset(TANGENT, stride, uvs ? 32 : 24);

                buffer.flush();
                buffer.withIndiceCount(totalIndexCount * 3);

                bmesh.material = currentMaterial;
                buffered.add(bmesh);

                currentMaterial = mat;
                position = limit;
                totalIndexCount = 0;
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

    private static void vertex(Vertex vertex, FloatBuffer vertexBuffer, boolean normals, boolean tangents, boolean uvs) {
        float[] buffer = new float[3];
        vertex.pos.get(buffer);
        vertexBuffer.put(buffer);
        if (normals && vertex.normal != null) {
            vertex.normal.get(buffer);
            vertexBuffer.put(buffer);
        }
        if (uvs && vertex.uv != null) {
            vertex.uv.get(buffer);
            vertexBuffer.put(buffer, 0, 2);
        }
        if (tangents && vertex.tangent != null) {
            vertex.tangent.get(buffer);
            vertexBuffer.put(buffer);
        }
    }

    public static class BufferedMesh {
        public Material material;
        public ITesselator buffer;

        public void destroy() {
            // TODO: destroy buffer
        }
    }
}