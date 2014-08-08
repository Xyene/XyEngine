/*
 * This file is part of XyEngine.
 *
 * XyEngine is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * XyEngine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with XyEngine. If not, see
 * <http://www.gnu.org/licenses/>.
 */

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
