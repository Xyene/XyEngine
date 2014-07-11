package tk.ivybits.engine.gl.scene.gl20;

import tk.ivybits.engine.gl.shader.ISceneShader;
import tk.ivybits.engine.scene.ITesselator;
import tk.ivybits.engine.scene.VertexAttribute;

import java.nio.FloatBuffer;
import java.util.Arrays;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.scene.VertexAttribute.UV_BUFFER;

class GL20Tesselator implements ITesselator {
    private GL20DrawContext gl20DrawContext;
    public int[] locations = new int[VertexAttribute.values().length];
    public int[] strides = new int[VertexAttribute.values().length];
    public int[] offsets = new int[VertexAttribute.values().length];
    public int handle;
    private int indiceCount;
    private ISceneShader lastShader = null;

    public GL20Tesselator(GL20DrawContext gl20DrawContext) {
        this.gl20DrawContext = gl20DrawContext;
        Arrays.fill(locations, -1);
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
        ISceneShader shader = gl20DrawContext.PARENT.currentGeometryShader;
        int location = shader.getAttributeLocation(attr);
        locations[attr.ordinal()] = location;
        strides[attr.ordinal()] = stride;
        offsets[attr.ordinal()] = offset;
        if (location > -1) {
            glVertexAttribPointer(
                    location,
                    attr != UV_BUFFER ? 3 : 2,
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
        glBindBuffer(GL_ARRAY_BUFFER, handle);
        // Update for new shader
        if (gl20DrawContext.PARENT.currentGeometryShader != lastShader) {
            lastShader = gl20DrawContext.PARENT.currentGeometryShader;
            for (VertexAttribute attr : VertexAttribute.values()) {
                withOffset(attr, strides[attr.ordinal()], offsets[attr.ordinal()]);
            }
        }
        for (VertexAttribute attr : VertexAttribute.values()) {
            int location = locations[attr.ordinal()];
            if (location > -1) {
                glEnableVertexAttribArray(location);
            }
        }
      //  glBindBuffer(GL_ARRAY_BUFFER, handle);
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
                glVertexAttribPointer(location, attr != UV_BUFFER ? 3 : 2, GL_FLOAT, false, strides[ordinal], offsets[ordinal]);
            }
        }
    }
}
