package tk.ivybits.engine.gl.scene.gl20.bloom;

import tk.ivybits.engine.gl.texture.IFramebuffer;
import tk.ivybits.engine.gl.texture.ISampledFramebuffer;

import java.nio.ByteBuffer;

import static tk.ivybits.engine.gl.GL.*;

public class BloomFBO implements ISampledFramebuffer {
    private int texture;
    private int fbo;
    private int depthBuffer;
    private int width, height;

    public BloomFBO(int width, int height) {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glBindTexture(GL_TEXTURE_2D, 0);

        depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);

        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);

        int status;
        if ((status = glCheckFramebufferStatus(GL_FRAMEBUFFER)) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X\n", status));
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void bindTexture() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    @Override
    public void unbindTexture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public void bindFramebuffer() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fbo);
    }

    @Override
    public void unbindFramebuffer() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    @Override
    public int getFramebufferId() {
        return fbo;
    }

    @Override
    public int getTextureId() {
        return texture;
    }

    private void blit(int id) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fbo);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, id);

        glBlitFramebuffer(0, 0, width, height, 0, 0, width, height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
        glBindFramebuffer(GL_READ_FRAMEBUFFER, 0);
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    @Override
    public void blit() {
        blit(0);
    }

    @Override
    public void blit(IFramebuffer other) {
        blit(other.getFramebufferId());
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glBindTexture(GL_TEXTURE_2D, 0);

        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    @Override
    public void destroy() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glDeleteFramebuffers(fbo);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glDeleteRenderbuffers(depthBuffer);
        glDeleteTextures(texture);
    }
}
