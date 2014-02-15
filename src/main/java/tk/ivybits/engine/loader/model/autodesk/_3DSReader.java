package tk.ivybits.engine.loader.model.autodesk;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.scene.model.IModelReader;
import tk.ivybits.engine.loader.io.LittleEndianInputStream;

import java.io.*;

public class _3DSReader implements IModelReader {
    final int CHUNK_VERSION = 0x0002;
    final int CHUNK_RGBF = 0x0010;
    final int CHUNK_RGBB = 0x0011;

    final int CHUNK_PERCENTW = 0x0030;
    final int CHUNK_PERCENTF = 0x0031;

    final int CHUNK_PRJ = 0xC23D;
    final int CHUNK_MLI = 0x3DAA;

    final int CHUNK_MAIN = 0x4D4D;
    final int CHUNK_OBJMESH = 0x3D3D;
    final int CHUNK_ONEUNIT = 0x0100;
    final int CHUNK_BKGCOLOR = 0x1200;
    final int CHUNK_AMBCOLOR = 0x2100;
    final int CHUNK_DEFAULT_VIEW = 0x3000;
    final int CHUNK_VIEW_TOP = 0x3010;
    final int CHUNK_VIEW_BOTTOM = 0x3020;
    final int CHUNK_VIEW_LEFT = 0x3030;
    final int CHUNK_VIEW_RIGHT = 0x3040;
    final int CHUNK_VIEW_FRONT = 0x3050;
    final int CHUNK_VIEW_BACK = 0x3060;
    final int CHUNK_VIEW_USER = 0x3070;
    final int CHUNK_VIEW_CAMERA = 0x3080;
    final int CHUNK_OBJBLOCK = 0x4000;
    final int CHUNK_TRIMESH = 0x4100;
    final int CHUNK_VERTFLAGS = 0x4111;
    final int CHUNK_FACELIST = 0x4120;
    final int CHUNK_FACEMAT = 0x4130;
    final int CHUNK_MAPLIST = 0x4140;
    final int CHUNK_SMOOLIST = 0x4150;
    final int CHUNK_TRMATRIX = 0x4160;
    final int CHUNK_MESHCOLOR = 0x4165;
    final int CHUNK_TXTINFO = 0x4170;
    final int CHUNK_LIGHT = 0x4600;
    final int CHUNK_SPOTLIGHT = 0x4610;
    final int CHUNK_CAMERA = 0x4700;
    final int CHUNK_HIERARCHY = 0x4F00;

    final int CHUNK_VIEWPORT_LAYOUT_OLD = 0x7000;
    final int CHUNK_VIEWPORT_DATA_OLD = 0x7010;
    final int CHUNK_VIEWPORT_SIZE = 0x7020;
    final int CHUNK_NETWORK_VIEW = 0X7030;

    final int CHUNK_VIEWPORT_LAYOUT = 0x7001;
    final int CHUNK_VIEWPORT_DATA = 0x7011;
    final int CHUNK_VIEWPORT_DATA3 = 0x7012;

    final int CHUNK_MATERIAL = 0xAFFF;
    final int CHUNK_MATNAME = 0xA000;
    final int CHUNK_AMBIENT = 0xA010;
    final int CHUNK_DIFFUSE = 0xA020;
    final int CHUNK_SPECULAR = 0xA030;
    final int CHUNK_TEXTURE = 0xA200;
    final int CHUNK_BUMPMAP = 0xA230;
    final int CHUNK_MAPFILE = 0xA300;
    final int CHUNK_KEYFRAMER = 0xB000;
    final int CHUNK_AMBIENTKEY = 0xB001;
    final int CHUNK_TRACKINFO = 0xB002;
    final int CHUNK_TRACKOBJNAME = 0xB010;
    final int CHUNK_TRACKPIVOT = 0xB013;
    final int CHUNK_TRACKPOS = 0xB020;
    final int CHUNK_TRACKROTATE = 0xB021;
    final int CHUNK_TRACKSCALE = 0xB022;
    final int CHUNK_TRACKMORPH = 0xB026;
    final int CHUNK_TRACKHIDE = 0xB029;
    final int CHUNK_OBJNUMBER = 0xB030;
    final int CHUNK_TRACKCAMERA = 0xB003;
    final int CHUNK_TRACKFOV = 0xB023;
    final int CHUNK_TRACKROLL = 0xB024;
    final int CHUNK_TRACKCAMTGT = 0xB004;
    final int CHUNK_TRACKLIGHT = 0xB005;
    final int CHUNK_TRACKLIGTGT = 0xB006;
    final int CHUNK_TRACKSPOTL = 0xB007;
    final int CHUNK_FRAMES = 0xB008;

    @Override
    public IGeometry load(File in) throws IOException {
        LittleEndianInputStream reader = new LittleEndianInputStream(new BufferedInputStream(new FileInputStream(in)));

        float[] vertices;

        ChunkHeader main = readBlock(reader);
        if(main.type != CHUNK_MAIN) {
            throw new IllegalArgumentException(String.format("invalid 3DS file: root chunk should be 0x4D4D, not 0x%04X", main.type));
        }
        int limit = main.size;
        int read = 6;

        while (read < limit) {
            ChunkHeader chunk = readBlock(reader);

            System.out.printf("Chunk 0x%04X (+%d)\n", chunk.type, read);
            read += chunk.size;
            switch (chunk.type) {
                case CHUNK_VERSION:
                    int version = reader.readInt();
                    System.out.printf("3DS v0x%04X\n", version);
                    break;
                case 0x3D3D: // Editor chunk
                case 0x4000: // +- Object block
                case 0x4100: //    +- Triangular mesh
                    break;
                case 0x4110: //       +- Vertices
                    short vertexCount = reader.readShort();
                    System.out.println(vertexCount * 3 + " vertices found.");
                    vertices = new float[vertexCount * 3];
                    for (int i = 0; i < vertices.length; i++) {
                        vertices[i] = reader.readFloat();
                    }
                    break;
                case 0xAFFF: // Material
                case 0xA200: // Texture map
                    break;
                default:
                    reader.skip(chunk.size - 6);
                    break;
            }
        }
        return null;
    }

    private ChunkHeader readBlock(LittleEndianInputStream reader) throws IOException {
        ChunkHeader chunk = new ChunkHeader();
        chunk.type = reader.readShort();
        chunk.size = reader.readInt();
        return chunk;
    }

    class ChunkHeader {
        int type;
        int size;
    }

    private synchronized void parseChunks(DataInputStream in) {

    }

    @Override
    public String getFileType() {
        return "3DS";
    }
}
