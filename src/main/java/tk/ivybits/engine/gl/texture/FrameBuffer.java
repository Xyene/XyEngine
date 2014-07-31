package tk.ivybits.engine.gl.texture;

import java.util.LinkedList;
import java.util.List;

import static tk.ivybits.engine.gl.GL.*;

public class FrameBuffer {
    private int width;
    private int height;
    private int handle;
    private List<RenderBuffer> renderBufferList = new LinkedList<>();
    private Texture colorBuffer;
    private boolean bound = false;

    public FrameBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        handle = glGenFramebuffers();
        resize(width, height);
        int status;
        if ((status = glCheckFramebufferStatus(GL_FRAMEBUFFER)) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X\n", status));
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public FrameBuffer attach(RenderBuffer buffer) {
        boolean b = bound;
        if (!b) bind();
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, buffer.target(), GL_RENDERBUFFER, buffer.id());
        if (!b) unbind();
        renderBufferList.add(buffer);
        return this;
    }

    public FrameBuffer attach(Texture buffer) {
        boolean b = bound;
        if (!b) bind();
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, buffer.target(), buffer.id(), 0);
        if (!b) unbind();
        colorBuffer = buffer;
        return this;
    }

    public Texture getTexture() {
        return colorBuffer;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void bind() {
        bound = true;
        glBindFramebuffer(GL_FRAMEBUFFER, handle);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        bound = false;
    }

    public int id() {
        return handle;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        for (RenderBuffer buffer : renderBufferList) {
            buffer.resize(width, height);
        }
        if (colorBuffer != null) colorBuffer.resize(width, height);
    }

    public void destroy() {
        glDeleteFramebuffers(handle);
        for (RenderBuffer buffer : renderBufferList) {
            buffer.destroy();
        }
        if (colorBuffer != null) colorBuffer.destroy();
    }

    private void blit(int id) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, handle);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);

        glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL_COLOR_BUFFER_BIT, GL_NEAREST);

        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);
    }

    public void blit() {
        blit(0);
    }

    public void blit(FrameBuffer other) {
        blit(other.id());
    }

    public boolean isBound() {
        return bound;
    }
}
