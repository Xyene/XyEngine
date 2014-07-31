package tk.ivybits.engine.gl.scene.gl11;

import tk.ivybits.engine.scene.IDrawable;
import tk.ivybits.engine.scene.IScene;
import tk.ivybits.engine.scene.geometry.ITesselator;
import tk.ivybits.engine.util.FloatArrayList;

import static tk.ivybits.engine.gl.GL.*;

public class GL11Tesselator implements ITesselator {
    private final GL11DrawContext drawContext;
    private final int flags;
    private int primitiveType;
    private FloatArrayList vertices, normals, textures;

    public GL11Tesselator(GL11DrawContext drawContext, int flags, int primitiveType) {
        this.drawContext = drawContext;
        this.flags = flags;
        this.primitiveType = primitiveType;
        vertices = new FloatArrayList();
        if ((flags & NORMAL_ATTR) > 0) normals = new FloatArrayList();
        if ((flags & UV_ATTR) > 0) textures = new FloatArrayList();
    }

    @Override
    public int getType() {
        return flags;
    }

    @Override
    public void vertex(float x, float y, float z) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
    }

    @Override
    public void normal(float x, float y, float z) {
        if (normals == null) throw new IllegalStateException("buffer not initialized for normal inputs");
        normals.add(x);
        normals.add(y);
        normals.add(z);
    }

    @Override
    public void texture(float u, float v) {
        if (textures == null) throw new IllegalStateException("buffer not initialized for texture inputs");
        textures.add(u);
        textures.add(v);
    }

    @Override
    public void tangent(float x, float y, float z) {
        // Do nothing
    }

    class DisplayListDrawable implements IDrawable {
        private final int handle;

        public DisplayListDrawable(int handle) {
            this.handle = handle;
        }

        @Override
        public boolean isTransparent() {
            return false;
        }

        @Override
        public void draw(IScene scene) {
            glCallList(handle);
        }

        @Override
        public void destroy() {
            glDeleteLists(handle, 1);
        }

        @Override
        public int priority() {
            return -1;
        }
    }

    @Override
    public IDrawable create() {
        int indexCount = vertices.size() / 3;
        int vi = 0, ni = 0, ui = 0;

        int handle = glGenLists(1);
        glNewList(handle, GL_COMPILE);

        glBegin(primitiveType);
        for (int i = 0; i < indexCount; i++) {
            if (textures != null) glTexCoord2f(textures.get(ui++), textures.get(ui++));
            if (normals != null) glNormal3f(normals.get(ni++), normals.get(ni++), normals.get(ni++));
            glVertex3f(vertices.get(vi++), vertices.get(vi++), vertices.get(vi++));
        }
        glEnd();

        glEndList();

        vertices = normals = textures = null;

        return new DisplayListDrawable(handle);
    }
}
