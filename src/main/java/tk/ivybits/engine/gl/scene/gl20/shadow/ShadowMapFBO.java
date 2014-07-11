package tk.ivybits.engine.gl.scene.gl20.shadow;

import org.lwjgl.util.vector.Matrix4f;
import tk.ivybits.engine.scene.texture.IWriteableTexture;

import java.nio.ByteBuffer;

import static tk.ivybits.engine.gl.GL.*;

// TODO: create IFrameTexture
public class ShadowMapFBO implements IWriteableTexture {
    private final int shadowMapId;
    private final int fboId;
    private int width;
    private int height;
    public Matrix4f projection;
    private boolean forWrite, forRead;

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
    public void bind() {
        forRead = true;
        glBindTexture(GL_TEXTURE_2D, shadowMapId);
    }

    @Override
    public void unbind() {
        if (forRead)
            glBindTexture(GL_TEXTURE_2D, 0);
        if (forWrite)
            glBindFramebuffer(GL_DRAW_FRAMEBUFFER, 0);
        forRead = forWrite = false;
    }

    @Override
    public void bindForWriting() {
        forWrite = true;
        glBindFramebuffer(GL_DRAW_FRAMEBUFFER, fboId);
    }

    @Override
    public int id() {
        return shadowMapId;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    public void destroy() {
        glDeleteFramebuffers(fboId);
        glDeleteTextures(shadowMapId);
    }

    public void resize(int width, int height) {
        glBindTexture(GL_TEXTURE_2D, shadowMapId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.width = width, this.height = height, 0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
