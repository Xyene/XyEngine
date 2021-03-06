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

package tk.ivybits.engine.gl.scene.gl20;

import org.lwjgl.BufferUtils;
import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.util.FloatArrayList;

import java.nio.FloatBuffer;
import java.util.Arrays;

import static tk.ivybits.engine.gl.GL.*;

public class GL20Tesselator implements ITesselator {
    private final int flags;
    private int primitiveType;
    private FloatArrayList vertices, normals, tangents, textures;

    public GL20Tesselator(int flags, int primitiveType) {
        this.flags = flags;
        this.primitiveType = primitiveType;
        vertices = new FloatArrayList();
        if ((flags & NORMAL_BUFFER) > 0) normals = new FloatArrayList();
        if ((flags & TANGENT_BUFFER) > 0) tangents = new FloatArrayList();
        if ((flags & UV_BUFFER) > 0) textures = new FloatArrayList();
    }

    @Override
    public int getBufferFlags() {
        return flags;
    }

    @Override
    public void pushVertex(float x, float y, float z) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
    }

    @Override
    public void pushNormal(float x, float y, float z) {
        if (normals == null) return;
        normals.add(x);
        normals.add(y);
        normals.add(z);
    }

    @Override
    public void pushTexCoord(float u, float v) {
        if (textures == null) return;
        textures.add(u);
        textures.add(v);
    }

    @Override
    public void pushTangent(float x, float y, float z) {
        if (tangents == null) return;
        tangents.add(x);
        tangents.add(y);
        tangents.add(z);
    }

    private static final String[] VERTEX_ATTRIBUTES = {"a_vertex", "a_normal", "a_uv", "a_tangent"};
    private static final int[] VERTEX_ATTRIBUTE_SIZES = {3, 3, 2, 3};
    private static final int[] VERTEX_ATTRIBUTE_TYPES = {GL_FLOAT, GL_FLOAT, GL_FLOAT, GL_FLOAT};

    class InterleavedDrawable implements IDrawable {
        private final int handle;
        private final int indexCount;
        private int stride;
        private int lastShader = 0;
        public int[] locations = new int[VERTEX_ATTRIBUTES.length];
        public int[] offsets;

        public InterleavedDrawable(int handle, int indexCount, int stride, int[] offsets) {
            this.handle = handle;
            this.indexCount = indexCount;
            this.stride = stride;
            this.offsets = offsets;
            Arrays.fill(locations, -1);
        }

        private void setVertexPointers() {
            for (int i = 0; i < VERTEX_ATTRIBUTES.length; i++) {
                int location = locations[i];
                if (location > -1) {
                    glVertexAttribPointer(location, VERTEX_ATTRIBUTE_SIZES[i], VERTEX_ATTRIBUTE_TYPES[i], false, stride, offsets[i]);
                }
            }
        }

        @Override
        public boolean isTransparent() {
            return false;
        }

        @Override
        public void draw(IScene scene) {
            glBindBuffer(GL_ARRAY_BUFFER, handle);
            // Update for new shader
            int currentShader = glGetInteger(GL_CURRENT_PROGRAM);
            if (currentShader != lastShader) {
                lastShader = currentShader;
                for (int i = 0, vertex_attributesLength = VERTEX_ATTRIBUTES.length; i < vertex_attributesLength; i++) {
                    int location = glGetAttribLocation(currentShader, VERTEX_ATTRIBUTES[i]);
                    //System.out.println(VERTEX_ATTRIBUTES[i] +"-." + location);
                    locations[i] = location;
                    if (location > -1) {
                        glVertexAttribPointer(
                                location,
                                VERTEX_ATTRIBUTE_SIZES[i],
                                VERTEX_ATTRIBUTE_TYPES[i],
                                false,
                                stride,
                                offsets[i]);
                    }
                }
            }
            for (int i = 0, vertex_attributesLength = VERTEX_ATTRIBUTES.length; i < vertex_attributesLength; i++) {
                int location = locations[i];
                if (location > -1) {
                    glEnableVertexAttribArray(location);
                }
            }

            setVertexPointers();
            glDrawArrays(primitiveType, 0, indexCount);
        }

        @Override
        public void destroy() {
            glDeleteBuffers(handle);
        }

        @Override
        public int priority() {
            return -1;
        }
    }

    @Override
    public IDrawable createDrawable() {
        int indexSize = 3;
        int[] offsets = new int[VERTEX_ATTRIBUTES.length];
        int stride = 0;
        // Vertices
        indexSize += 3;
        offsets[0] = stride;
        stride += 12;

        if (normals != null) {
            indexSize += 3;
            offsets[1] = stride;
            stride += 12;
        }
        if (textures != null) {
            indexSize += 2;
            offsets[2] = stride;
            stride += 8;
        }
        if (tangents != null) {
            indexSize += 3;
            offsets[3] = stride;
            stride += 12;
        }

        int indexCount = vertices.size() / 3;
        FloatBuffer interleaved = BufferUtils.createFloatBuffer(indexCount * indexSize);

        int vi = 0, ni = 0, ti = 0, ui = 0;

        for (int i = 0; i < indexCount; i++) {
            for (int j = 0; j < 3; j++) interleaved.put(vertices.get(vi++));
            if (normals != null) for (int j = 0; j < 3; j++) interleaved.put(normals.get(ni++));
            if (textures != null) for (int j = 0; j < 2; j++) interleaved.put(textures.get(ui++));
            if (tangents != null) for (int j = 0; j < 3; j++) interleaved.put(tangents.get(ti++));
        }

        interleaved.flip();

        int handle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, handle);
        glBufferData(GL_ARRAY_BUFFER, interleaved, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        vertices = normals = textures = tangents = null;

        return new InterleavedDrawable(handle, indexCount, stride, offsets);
    }
}
