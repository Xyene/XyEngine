package tk.ivybits.engine.gl.texture;

import org.lwjgl.opengl.GLContext;
import tk.ivybits.engine.scene.texture.ITexture;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.EXTFramebufferObject.*;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;

public class FrameBuffer implements ITexture {
    private final int target;
    private int fbo_texture;
    private int fbo;
    private int rbo_depth;
    private int width, height;

    public FrameBuffer(int width, int height, int target) {
        this(width, height, target, GL_NEAREST, GL_CLAMP_TO_EDGE);
    }

    public FrameBuffer(int width, int height, int target, int filter, int wrap) {
        this.target = target;
        if (!GLContext.getCapabilities().GL_EXT_framebuffer_object)
            throw new UnsupportedOperationException("FBO extension unsupported");
        this.width = width;
        this.height = height;
        fbo_texture = glGenTextures();
        glBindTexture(target, fbo_texture);
        glTexParameteri(target, GL_TEXTURE_MAG_FILTER, filter);
        glTexParameteri(target, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(target, GL_TEXTURE_WRAP_S, wrap);
        glTexParameteri(target, GL_TEXTURE_WRAP_T, wrap);
        glTexImage2D(target, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glBindTexture(target, 0);

        rbo_depth = glGenRenderbuffersEXT();
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, rbo_depth);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT32, width, height);
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, 0);

        fbo = glGenFramebuffersEXT();
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);
        glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, target, fbo_texture, 0);
        glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT, rbo_depth);

        int status;
        if ((status = glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT)) != GL_FRAMEBUFFER_COMPLETE_EXT) {
            throw new IllegalStateException(String.format("failed to create framebuffer: 0x%04X\n", status));
        }
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
    }

    public void bind() {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, fbo);
    }

    public void unbind() {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
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

        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, rbo_depth);
        glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL_DEPTH_COMPONENT32, width, height);
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, 0);
        return this;
    }

    public void destroy() {
        glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
        glDeleteFramebuffersEXT(fbo);
        glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, 0);
        glDeleteRenderbuffersEXT(rbo_depth);
        glDeleteTextures(fbo_texture);
    }

    public int getTexture() {
        return fbo_texture;
    }
}
