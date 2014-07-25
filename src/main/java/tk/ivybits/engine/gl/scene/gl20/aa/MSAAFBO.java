package tk.ivybits.engine.gl.scene.gl20.aa;

import tk.ivybits.engine.gl.texture.IFramebuffer;

import static tk.ivybits.engine.gl.GL.*;

public class MSAAFBO implements IFramebuffer {
    private int samples;
    private int fbo;
    private int colorBuffer;
    private int depthBuffer;
    private int width, height;

    public MSAAFBO(int width, int height, int samples) {
        this.width = width;
        this.height = height;
        this.samples = samples;

        fbo = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);

        createAttachments();
        bindAttachments();

        int status;
        if ((status = glCheckFramebufferStatus(GL_FRAMEBUFFER)) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X", status));
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
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
    public void blit(IFramebuffer where) {
        blit(where.getFramebufferId());
    }

    private void bindAttachments() {
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D_MULTISAMPLE, colorBuffer, 0);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBuffer);
    }

    private void createAttachments() {
        colorBuffer = glGenTextures();
        glBindTexture(GL_TEXTURE_2D_MULTISAMPLE, colorBuffer);
        glTexImage2DMultisample(GL_TEXTURE_2D_MULTISAMPLE, samples, GL_RGBA8, width, height, false);

        depthBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, depthBuffer);
        glRenderbufferStorageMultisample(GL_RENDERBUFFER, samples, GL_DEPTH_COMPONENT16, width, height);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
    }

    private void updateAttachments() {
        glDeleteTextures(colorBuffer);
        glDeleteRenderbuffers(depthBuffer);
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
        createAttachments();
        bindAttachments();
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void destroy() {
        glDeleteTextures(colorBuffer);
        glDeleteRenderbuffers(depthBuffer);
        glDeleteFramebuffers(fbo);
    }

    public int getSamples() {
        return samples;
    }

    public void setSamples(int samples) {
        this.samples = samples;
        updateAttachments();
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        updateAttachments();
    }

    @Override
    public void bindFramebuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, fbo);
    }

    @Override
    public void unbindFramebuffer() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public int getFramebufferId() {
        return colorBuffer;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }
}
