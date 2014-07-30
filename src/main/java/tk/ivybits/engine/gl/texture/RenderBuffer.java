package tk.ivybits.engine.gl.texture;

import static tk.ivybits.engine.gl.GL.*;
import static tk.ivybits.engine.gl.GL.GL_RENDERBUFFER;
import static tk.ivybits.engine.gl.GL.glDeleteRenderbuffers;

public class RenderBuffer {
    private final int target;
    private int internalTarget;
    private int width;
    private int height;
    private final int samples;
    private int buffer;
    private boolean bound = false;

    public RenderBuffer(int target, int internalTarget, int width, int height, int samples) {
        this.target = target;
        this.internalTarget = internalTarget;
        this.width = width;
        this.height = height;
        this.samples = samples;
        buffer = glGenRenderbuffers();
        resize(width, height);
    }

    public int target() {
        return target;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void bind() {
        bound = true;
        glBindRenderbuffer(GL_RENDERBUFFER, buffer);
    }

    public void unbind() {
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        bound = false;
    }

    public int id() {
        return buffer;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        boolean b = bound;
        if (!b) bind();
        if (samples > 0)
            glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, internalTarget, width, height);
        else
            glRenderbufferStorage(GL_RENDERBUFFER, internalTarget, width, height);
        if (!b) unbind();
    }

    public void destroy() {
        glDeleteRenderbuffers(buffer);
    }

    public boolean isBound() {
        return bound;
    }

    public static RenderBuffer newDepthBuffer(int width, int height) {
        return new RenderBuffer(GL_DEPTH_ATTACHMENT, GL_DEPTH_COMPONENT, width, height, 0);
    }

    public static RenderBuffer newMultisampledDepthBuffer(int width, int height, int samples) {
        return new RenderBuffer(GL_DEPTH_ATTACHMENT, GL_DEPTH_COMPONENT, width, height, samples);
    }
}
