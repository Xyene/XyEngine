package tk.ivybits.engine.gl.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import static tk.ivybits.engine.gl.GL.*;
import static org.lwjgl.util.glu.GLU.gluBuild2DMipmaps;
import static tk.ivybits.engine.gl.GL.glGenerateMipmap;
import static tk.ivybits.engine.gl.GL.glTexImage2D;

public class TextureLoader {
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

    public static void xyTextureData(int target, int id, BufferedImage in) {
        xyTextureData(target, id, in, true);
    }

    public static void xyTextureData(int target, int id, BufferedImage in, boolean mipmap) {
        glBindTexture(GL_TEXTURE_2D, id);

        byte[] data;
        int srcPixelFormat;
        boolean alpha = in.getColorModel().hasAlpha();

        if (in.getColorModel().hasAlpha()) {
            srcPixelFormat = GL_RGBA;
        } else {
            srcPixelFormat = GL_RGB;
        }
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        int texWidth = _get2Fold(in.getWidth());
        int texHeight = _get2Fold(in.getHeight());

        // create a raster that can be used by OpenGL as a source
        // for a texture
        if (in.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
            texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0F, 0F, 0F, 0F));
        g.fillRect(0, 0, texWidth, texHeight);
        g.drawImage(in, 0, 0, null);
        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        ByteBuffer textureBuffer = imageBuffer;

        if (target == GL_TEXTURE_2D) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            // glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
        }

        // produce a texture from the byte buffer
        glTexImage2D(target,
                0,
                GL_RGBA,
                _get2Fold(texImage.getWidth()),
                _get2Fold(texImage.getHeight()),
                0,
                srcPixelFormat,
                GL_UNSIGNED_BYTE,
                textureBuffer);

        data = ((DataBufferByte) raster.getDataBuffer()).getData();

        ByteBuffer nativeBuffer = ByteBuffer.allocateDirect(data.length);
        nativeBuffer.order(ByteOrder.nativeOrder());
        nativeBuffer.put(data, 0, data.length);
        nativeBuffer.flip();

        if (mipmap) {
            if (GLContext.getCapabilities().OpenGL30) {
                glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
            } else {
                gluBuild2DMipmaps(target, alpha ? 4 : 3, texWidth - 1, texHeight - 1, srcPixelFormat, GL_UNSIGNED_BYTE, nativeBuffer);
                return;
            }
        }
        glTexImage2D(target, 0, alpha ? GL_RGBA : GL_RGB, texWidth, texHeight, 0, srcPixelFormat, GL_UNSIGNED_BYTE, nativeBuffer);
    }

    public static int getSystemTexture(String name, int target, int filter, boolean mipmapped) throws IOException {
        return getTexture(ImageIO.read(ClassLoader.getSystemResourceAsStream(name)), target, filter, mipmapped);
    }

    public static int getTexture(BufferedImage in, int target, int filter, boolean mipmapped) {
        if (in == null) throw new IllegalArgumentException("input image must not be null");
        IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
        glGenTextures(textureIDBuffer);
        int id = textureIDBuffer.get(0);

        int texWidth = in.getWidth();
        int texHeight = in.getHeight();

        glBindTexture(GL_TEXTURE_2D, id);

        byte[] data;
        int srcPixelFormat;
        boolean alpha = in.getColorModel().hasAlpha();

        if (in.getColorModel().hasAlpha()) {
            srcPixelFormat = GL_RGBA;
        } else {
            srcPixelFormat = GL_RGB;
        }
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;

        texWidth = _get2Fold(in.getWidth());
        texHeight = _get2Fold(in.getHeight());

        // create a raster that can be used by OpenGL as a source
        // for a texture
        if (in.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
            texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0F, 0F, 0F, 0F));
        g.fillRect(0, 0, texWidth, texHeight);
        g.drawImage(in, 0, 0, null);
        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        ByteBuffer textureBuffer = imageBuffer;

        if (target == GL_TEXTURE_2D) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            // glGenerateMipmap(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
        }

        // produce a texture from the byte buffer
        glTexImage2D(target,
                0,
                GL_RGBA,
                _get2Fold(texImage.getWidth()),
                _get2Fold(texImage.getHeight()),
                0,
                srcPixelFormat,
                GL_UNSIGNED_BYTE,
                textureBuffer);

        data = ((DataBufferByte) raster.getDataBuffer()).getData();

        ByteBuffer nativeBuffer = ByteBuffer.allocateDirect(data.length);
        nativeBuffer.order(ByteOrder.nativeOrder());
        nativeBuffer.put(data, 0, data.length);
        nativeBuffer.flip();

        //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        //glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);

        if (false && mipmapped) {
            gluBuild2DMipmaps(target, 3, texWidth - 1, texHeight - 1, srcPixelFormat, GL_UNSIGNED_BYTE, nativeBuffer);
        } else {
            glTexImage2D(target, 0, alpha ? GL_RGBA : GL_RGB, texWidth, texHeight, 0, srcPixelFormat, GL_UNSIGNED_BYTE, nativeBuffer);
        }

        return id;
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
