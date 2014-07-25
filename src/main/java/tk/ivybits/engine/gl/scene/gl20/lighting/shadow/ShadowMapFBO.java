package tk.ivybits.engine.gl.scene.gl20.lighting.shadow;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.gl.texture.IFramebuffer;
import tk.ivybits.engine.gl.texture.ISampledFramebuffer;

import java.nio.ByteBuffer;

import static tk.ivybits.engine.gl.GL.*;

public class ShadowMapFBO implements ISampledFramebuffer {
    private final int shadowMapId;
    private final int fboId;
    private int width;
    private int height;
    public Matrix4f projection;

    public ShadowMapFBO(int width, int height) {
        this.width = width;
        this.height = height;
        this.fboId = glGenFramebuffers();
        this.shadowMapId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, shadowMapId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, width, height, 0, GL_DEPTH_COMPONENT, GL_UNSIGNED_INT, (ByteBuffer) null);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR); // GL_LINEAR & PCF?
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

        glBindFramebuffer(GL_FRAMEBUFFER, fboId);
        glFramebufferTexture2D(GL_DRAW_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, shadowMapId, 0);

        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);

        int status;
        if ((status = glCheckFramebufferStatus(GL_FRAMEBUFFER)) != GL_FRAMEBUFFER_COMPLETE) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X\n", status));
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void bindTexture() {
        glBindTexture(GL_TEXTURE_2D, shadowMapId);
    }

    @Override
    public void unbindTexture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    @Override
    public int getTextureId() {
        return shadowMapId;
    }

    @Override
    public void bindFramebuffer() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fboId);
    }

    @Override
    public void unbindFramebuffer() {
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
    }

    @Override
    public int getFramebufferId() {
        return fboId;
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
    public void destroy() {
        glDeleteFramebuffers(fboId);
        glDeleteTextures(shadowMapId);
    }

    @Override
    public void resize(int width, int height) {
        glBindTexture(GL_TEXTURE_2D, shadowMapId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width = width, this.height = height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void blit(int id) {
        glBindFramebuffer(GL_READ_FRAMEBUFFER, fboId);
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
}
