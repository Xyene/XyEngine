package tk.ivybits.engine.io.model.md2;

import tk.ivybits.engine.io.LittleEndianInputStream;
import tk.ivybits.engine.io.model.IModelReader;
import tk.ivybits.engine.scene.model.Model;

import java.io.*;

public class MD2Reader implements IModelReader {
    public Model load(File in) throws IOException {
        return load(in.getAbsolutePath(), RESOURCE_FINDER_FILE);
    }

    public Model loadSystem(String in) throws IOException {
        return load(in, RESOURCE_FINDER_PACKAGED_RESOURCE);
    }

    @Override
    public Model load(String source, ResourceFinder finder) throws IOException {
        byte[] buffer = readBytes(finder.open(source));

        MD2Header header = readHeader(buffer);
        float[] texCoords = loadTexCoords(header, buffer);
        MD2Triangle[] triangles = loadTriangles(header, buffer);
        MD2Frame[] frames = loadFrames(header, buffer);

        System.out.printf("Skin: %dx%d\n", header.skinwidth, header.skinheight);
        System.out.println(header.num_skins);
        return null;
    }

    private static MD2Frame[] loadFrames(MD2Header header, byte[] bytes) throws IOException {
        LittleEndianInputStream in = new LittleEndianInputStream(new ByteArrayInputStream(bytes));
        in.skip(header.ofs_frames);

        MD2Frame[] frames = new MD2Frame[header.num_frames];
        for (int i = 0; i < header.num_frames; i++) {
            MD2Frame frame = new MD2Frame();
            frame.vertices = new float[header.num_xyz * 3];

            float scaleX = in.readFloat() / 25.0f, scaleY = in.readFloat() / 25.0f, scaleZ = in.readFloat() / 25.0f;
            float transX = in.readFloat() / 25.0f, transY = in.readFloat() / 25.0f, transZ = in.readFloat() / 25.0f;
            in.readFully(charBuffer);

            int len = 0;
            for (int i1 = 0; i1 < charBuffer.length; i1++)
                if (charBuffer[i1] == 0) {
                    len = i1 - 1;
                    break;
                }

            frame.name = new String(charBuffer, 0, len);

            System.out.println("> " + frame.name);

            for (int v = 0; v < header.num_xyz; v++) {

                frame.vertices[v * 3] = in.read() * scaleY + transY;
                frame.vertices[v * 3 + 1] = in.read() * scaleZ + transZ;
                frame.vertices[v * 3 + 2] = in.read() * scaleX + transX;

                in.read(); // normal index
            }

            frames[i] = frame;
        }

        in.close();

        return frames;
    }

    private static final byte[] charBuffer = new byte[16];

    private static MD2Triangle[] loadTriangles(MD2Header header, byte[] bytes) throws IOException {
        LittleEndianInputStream in = new LittleEndianInputStream(new ByteArrayInputStream(bytes));
        in.skip(header.ofs_tris);
        MD2Triangle[] triangles = new MD2Triangle[header.num_tris];

        for (int i = 0; i < header.num_tris; i++) {
            MD2Triangle triangle = new MD2Triangle();
            triangle.vertices[0] = in.readShort();
            triangle.vertices[1] = in.readShort();
            triangle.vertices[2] = in.readShort();
            triangle.texCoords[0] = in.readShort();
            triangle.texCoords[1] = in.readShort();
            triangle.texCoords[2] = in.readShort();
            triangles[i] = triangle;
        }
        in.close();

        return triangles;
    }

    private static float[] loadTexCoords(MD2Header header, byte[] bytes) throws IOException {
        LittleEndianInputStream in = new LittleEndianInputStream(new ByteArrayInputStream(bytes));
        in.skip(header.ofs_st);
        float[] texCoords = new float[header.num_st * 2];
        float width = header.skinwidth;
        float height = header.skinheight;

        for (int i = 0; i < header.num_st * 2; i += 2) {
            short u = in.readShort();
            short v = in.readShort();
            texCoords[i] = u / width;
            texCoords[i + 1] = v / height;
        }
        in.close();
        return texCoords;
    }


    private static MD2Header readHeader(byte[] source) throws IOException {
        LittleEndianInputStream in = new LittleEndianInputStream(new ByteArrayInputStream(source));
        MD2Header header = new MD2Header();

        header.ident = in.readInt();
        if (header.ident != 0x32504449)
            throw new IllegalArgumentException("invalid magic " + header.ident);

        header.version = in.readInt();
        if (header.version != 8)
            System.err.println("only MD2 version 8 supported, got version " + header.version);

        header.skinwidth = in.readInt();
        header.skinheight = in.readInt();
        header.framesize = in.readInt();

        header.num_skins = in.readInt();
        header.num_xyz = in.readInt();
        header.num_st = in.readInt();
        header.num_tris = in.readInt();
        header.num_glcmds = in.readInt();
        header.num_frames = in.readInt();

        header.ofs_skins = in.readInt();
        header.ofs_st = in.readInt();
        header.ofs_tris = in.readInt();
        header.ofs_frames = in.readInt();
        header.ofs_glcmds = in.readInt();
        // TODO: buffer until end
        header.ofs_end = in.readInt();

        return header;
    }

    private static byte[] readBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int readBytes = 0;
        while ((readBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, readBytes);
        }

        out.close();
        return out.toByteArray();
    }


    @Override
    public String getFileType() {
        return "MD2";
    }

}
