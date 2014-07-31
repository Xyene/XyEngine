package tk.ivybits.engine.gl;

import org.lwjgl.opengl.EXTDirectStateAccess;
import org.lwjgl.opengl.GLContext;

import static tk.ivybits.engine.gl.GL.*;

class GLDirectAccess {
    public static void glTexParameteri(int texture, int target, int key, int value) {
        if (GLContext.getCapabilities().GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glTextureParameteriEXT(texture, target, key, value);
        } else {
            int pre = glGetInteger(GL_TEXTURE_BINDING_2D);
            glBindTexture(target, texture);
            GL.glTexParameteri(target, key, value);
            glBindTexture(target, pre);
        }
    }

    public static void glTexParameterf(int texture, int target, int key, int value) {
        glTexParameteri(texture, target, key, value);
    }

    public static void glTexImage2D(int texture, int target, int level, int internalformat, int width, int height, int border, int format, int type, java.nio.ByteBuffer pixels) {
        if (GLContext.getCapabilities().GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            int pre = glGetInteger(GL_TEXTURE_BINDING_2D);
            glBindTexture(target, texture);
            GL.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
            glBindTexture(target, pre);
        }
    }

    public static void glTexImage2D(int texture, int target, int level, int internalformat, int width, int height, int border, int format, int type, java.nio.DoubleBuffer pixels) {
        if (GLContext.getCapabilities().GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            int pre = glGetInteger(GL_TEXTURE_BINDING_2D);
            glBindTexture(target, texture);
            GL.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
            glBindTexture(target, pre);
        }
    }

    public static void glTexImage2D(int texture, int target, int level, int internalformat, int width, int height, int border, int format, int type, java.nio.FloatBuffer pixels) {
        if (GLContext.getCapabilities().GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            int pre = glGetInteger(GL_TEXTURE_BINDING_2D);
            glBindTexture(target, texture);
            GL.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
            glBindTexture(target, pre);
        }
    }

    public static void glTexImage2D(int texture, int target, int level, int internalformat, int width, int height, int border, int format, int type, java.nio.IntBuffer pixels) {
        if (GLContext.getCapabilities().GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            int pre = glGetInteger(GL_TEXTURE_BINDING_2D);
            glBindTexture(target, texture);
            GL.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
            glBindTexture(target, pre);
        }
    }

    public static void glTexImage2D(int texture, int target, int level, int internalformat, int width, int height, int border, int format, int type, java.nio.ShortBuffer pixels) {
        if (GLContext.getCapabilities().GL_EXT_direct_state_access) {
            EXTDirectStateAccess.glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels);
        } else {
            int pre = glGetInteger(GL_TEXTURE_BINDING_2D);
            glBindTexture(target, texture);
            GL.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
            glBindTexture(target, pre);
        }
    }
}
