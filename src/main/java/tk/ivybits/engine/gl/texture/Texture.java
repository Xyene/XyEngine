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

package tk.ivybits.engine.gl.texture;

import org.lwjgl.opengl.GLContext;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.nio.*;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Set;
import java.util.WeakHashMap;

import static org.lwjgl.util.glu.GLU.gluBuild2DMipmaps;
import static tk.ivybits.engine.gl.GL.*;

public class Texture {
    private int target;
    private int format;
    private int width;
    private int height;
    private int handle;
    private boolean bound = false;
    public static final Set<Texture> ALL = Collections.newSetFromMap(new WeakHashMap<Texture, Boolean>());

    public Texture(int target, int format, int width, int height) {
        this.target = target;
        this.format = format;
        this.width = width;
        this.height = height;
        handle = glGenTextures();
        resize(width, height);
        ALL.add(this);
    }

    public Texture(BufferedImage in, boolean mipmap) {
        target = GL_TEXTURE_2D;
        handle = glGenTextures();
        byte[] data;
        int srcPixelFormat = in.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB;
        boolean alpha = in.getColorModel().hasAlpha();

        WritableRaster raster;
        BufferedImage texImage;

        width = _get2Fold(in.getWidth());
        height = _get2Fold(in.getHeight());

        if (in.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, width, height, 3, null);
            texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        Graphics2D g = texImage.createGraphics();
        g.setColor(new Color(0F, 0F, 0F, 0F));
        g.fillRect(0, 0, width, height);
        g.drawImage(in, 0, 0, null);

        data = ((DataBufferByte) raster.getDataBuffer()).getData();

        ByteBuffer nativeBuffer = ByteBuffer.allocateDirect(data.length);
        nativeBuffer.order(ByteOrder.nativeOrder());
        nativeBuffer.put(data, 0, data.length);
        nativeBuffer.flip();

        if (mipmap) {
            glBindTexture(GL_TEXTURE_2D, handle);
            gluBuild2DMipmaps(target, this.format = alpha ? 4 : 3, width, height, srcPixelFormat, GL_UNSIGNED_BYTE, nativeBuffer);
            glBindTexture(GL_TEXTURE_2D, 0);
        } else {
            glTexImage2D(handle, target, 0, this.format = alpha ? GL_RGBA : GL_RGB, width, height, 0, srcPixelFormat, GL_UNSIGNED_BYTE, nativeBuffer);
        }
        ALL.add(this);
    }

    public Texture setParameter(int key, int value) {
        glTexParameteri(handle, target, key, value);
        return this;
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

    public Texture bind() {
        glBindTexture(target, handle);
        bound = true;
        return this;
    }

    public Texture unbind() {
        glBindTexture(target, 0);
        bound = false;
        return this;
    }

    public int id() {
        return handle;
    }

    public void destroy() {
        glDeleteTextures(handle);
    }

    public Texture resize(int width, int height) {
        this.width = width;
        this.height = height;
        return setData(GL_UNSIGNED_BYTE, null);
    }

    public Texture setData(int type, Buffer buffer) {
        if (buffer == null)
            glTexImage2D(handle, target, 0, format, width, height, 0, format, type, (ByteBuffer) null);
        else if (buffer instanceof ByteBuffer)
            glTexImage2D(handle, target, 0, format, width, height, 0, format, type, (ByteBuffer) buffer);
        else if (buffer instanceof FloatBuffer)
            glTexImage2D(handle, target, 0, format, width, height, 0, format, type, (FloatBuffer) buffer);
        else if (buffer instanceof DoubleBuffer)
            glTexImage2D(handle, target, 0, format, width, height, 0, format, type, (DoubleBuffer) buffer);
        else if (buffer instanceof IntBuffer)
            glTexImage2D(handle, target, 0, format, width, height, 0, format, type, (IntBuffer) buffer);
        else if (buffer instanceof ShortBuffer)
            glTexImage2D(handle, target, 0, format, width, height, 0, format, type, (ShortBuffer) buffer);
        return this;
    }

    public boolean isBound() {
        return bound;
    }

    private static boolean _isPowerOf2(int n) {
        return (n & (n - 1)) == 0;
    }

    private static int _get2Fold(int n) {
        if (GLContext.getCapabilities().GL_ARB_texture_non_power_of_two || _isPowerOf2(n))
            return n;
        int ret = 2;
        while (ret < n) {
            ret <<= 1;
        }
        return ret;
    }

    private static ColorModel glAlphaColorModel = new ComponentColorModel(
            ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[]{8, 8, 8, 8},
            true,
            false,
            ComponentColorModel.TRANSLUCENT,
            DataBuffer.TYPE_BYTE);
    private static ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
            new int[]{8, 8, 8, 0},
            false,
            false,
            ComponentColorModel.OPAQUE,
            DataBuffer.TYPE_BYTE);
}

