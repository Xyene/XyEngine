package tk.ivybits.engine.gl.texture;

import tk.ivybits.engine.scene.texture.IWriteableTexture;

import java.nio.ByteBuffer;

import static tk.ivybits.engine.gl.GL.*;

public class FrameBuffer implements IWriteableTexture {
    private final int target;
    private int fbo_texture;
    private int fbo;
    private int depthBuffer;
    private int width, height;

    public FrameBuffer(int width, int height, int target) {
        this(width, height, target, GL_NEAREST, GL_CLAMP_TO_EDGE);
    }

    public FrameBuffer(int width, int height, int target, int filter, int wrap) {
        this.target = target;
        fbo_texture = glGenTextures();
        glBindTexture(target, fbo_texture);
        glTexParameteri(target, GL_TEXTURE_MAG_FILTER, filter);
        glTexParameteri(target, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(target, GL_TEXTURE_WRAP_S, wrap);
        glTexParameteri(target, GL_TEXTURE_WRAP_T, wrap);
        glTexImage2D(target, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glBindTexture(target, 0);

        depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);

        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, target, fbo_texture, 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);

        int status;
        if ((status = glCheckFramebufferStatus(GL_FRAMEBUFFER)) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X\n", status));
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, fbo_texture);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    @Override
    public void bindForWriting() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
    }

    @Override
    public int id() {
        return fbo_texture;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    public FrameBuffer resize(int width, int height) {
        this.width = width;
        this.height = height;
        glBindTexture(target, fbo_texture);
        glTexImage2D(target, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glBindTexture(target, 0);

        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        return this;
    }

    public void destroy() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDeleteFramebuffers(fbo);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glDeleteRenderbuffers(depthBuffer);
        glDeleteTextures(fbo_texture);
    }

    public int fbo() {
        return fbo;
    }
}
