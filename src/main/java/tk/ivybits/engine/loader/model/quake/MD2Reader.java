package tk.ivybits.engine.loader.model.quake;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.scene.model.IModelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MD2Reader implements IModelReader {
    private static class md2_header_t {
        int skinwidth;              /* texture width */
        int skinheight;             /* texture height */

        int framesize;              /* size in bytes of a frame */

        int num_skins;              /* number of skins */
        int num_vertices;           /* number of vertices per frame */
        int num_st;                 /* number of texture coordinates */
        int num_tris;               /* number of triangles */
        int num_glcmds;             /* number of opengl commands */
        int num_frames;             /* number of frames */

        int offset_skins;           /* offset skin data */
        int offset_st;              /* offset texture coordinate data */
        int offset_tris;            /* offset triangle data */
        int offset_frames;          /* offset frame data */
        int offset_glcmds;          /* offset OpenGL command data */
        int offset_end;             /* offset end of file */
    }

    @Override
    public IGeometry load(File from) throws IOException {
        FileInputStream f = new FileInputStream(from);
        FileChannel ch = f.getChannel();
        MappedByteBuffer in = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());
        in.order(ByteOrder.LITTLE_ENDIAN);

        md2_header_t header = parseHeader(in);
        return null;
    }

    private md2_header_t parseHeader(MappedByteBuffer in) {
        int magic = in.getInt(0x00);
        if(magic != 0x32504449) {
            throw new IllegalArgumentException("invalid magic number: expected 0x32504449, got " + Integer.toHexString(magic));
        }
        int version = in.getInt(4);
        if(version != 8) {
            throw new IllegalArgumentException("unsupported MD2 version: " + version);
        }
        md2_header_t struct = new md2_header_t();
        struct.skinwidth = in.getInt(8);
        struct.skinheight = in.getInt(12);
        struct.framesize = in.getInt(16);
        struct.num_skins = in.getInt(20);
        struct.num_vertices = in.getInt(24);
        struct.num_st = in.getInt(28);
        struct.num_tris = in.getInt(32);
        struct.num_glcmds = in.getInt(36);
        struct.num_frames = in.getInt(40);
        struct.offset_skins = in.getInt(44);
        struct.offset_st = in.getInt(48);
        struct.offset_tris = in.getInt(52);
        struct.offset_frames = in.getInt(56);
        struct.offset_glcmds = in.getInt(60);
        struct.offset_end = in.getInt(64);

        return struct;
    }

    @Override
    public String getFileType() {
        return "MD2";
    }
}
