package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.gl.shader.IGeometryShader;
import tk.ivybits.engine.scene.ITesselator;
import tk.ivybits.engine.scene.VertexAttribute;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static tk.ivybits.engine.scene.VertexAttribute.UV;

class GL20Tesselator implements ITesselator {
    private GL20DrawContext gl20DrawContext;
    public int[] locations = new int[VertexAttribute.values().length];
    public int[] strides = new int[VertexAttribute.values().length];
    public int[] offsets = new int[VertexAttribute.values().length];
    public int handle;
    private int indiceCount;

    public GL20Tesselator(GL20DrawContext gl20DrawContext) {
        this.gl20DrawContext = gl20DrawContext;
    }

    @Override
    public ITesselator update(FloatBuffer buffer) {
        if (handle != 0) {
            glDeleteBuffers(handle);
        }
        handle = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, handle);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        return this;
    }

    @Override
    public ITesselator withOffset(VertexAttribute attr, int stride, int offset) {
        IGeometryShader shader = gl20DrawContext.parent.getDefaultShader();
        int location = shader.getAttributeLocation(attr);
        System.out.println(attr + " -> " + location);
        locations[attr.ordinal()] = location;
        strides[attr.ordinal()] = stride;
        offsets[attr.ordinal()] = offset;
        if (location > -1) {
            glVertexAttribPointer(
                    location,
                    attr != UV ? 3 : 2,
                    GL_FLOAT,
                    false,
                    stride,
                    offset);
        }
        return this;
    }

    @Override
    public void withIndiceCount(int indiceCount) {
        this.indiceCount = indiceCount;
    }

    @Override
    public ITesselator flush() {
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        return this;
    }

    @Override
    public ITesselator draw(int type) {
        glPushClientAttrib(GL_CLIENT_VERTEX_ARRAY_BIT);
        for (VertexAttribute attr : VertexAttribute.values()) {
            int ordinal = attr.ordinal();
            int location = locations[ordinal];
            if (location > -1) {
                glEnableVertexAttribArray(location);
            }
        }
        glBindBuffer(GL_ARRAY_BUFFER, handle);
        setVertexPointers();
        glDrawArrays(type, 0, indiceCount);
        glPopClientAttrib();
        return this;
    }

    private void setVertexPointers() {
        for (VertexAttribute attr : VertexAttribute.values()) {
            int ordinal = attr.ordinal();
            int location = locations[ordinal];
            if (location > -1) {
                glVertexAttribPointer(location, 3, GL_FLOAT, false, strides[ordinal], offsets[ordinal]);
            }
        }
    }
}
