package tk.ivybits.engine.io.model.md2;

public class MD2Header {
    int ident;              // magic number. must be equal to "IDP2"
    int version;            // md2 version. must be equal to 8

    int skinwidth;          // width of the texture
    int skinheight;         // height of the texture
    int framesize;          // size of one frame in bytes

    int num_skins;          // number of textures
    int num_xyz;            // number of vertices
    int num_st;             // number of texture coordinates
    int num_tris;           // number of triangles
    int num_glcmds;         // number of opengl commands
    int num_frames;         // total number of frames

    int ofs_skins;          // offset to skin names (64 bytes each)
    int ofs_st;             // offset to s-t texture coordinates
    int ofs_tris;           // offset to triangles
    int ofs_frames;         // offset to frame data
    int ofs_glcmds;         // offset to opengl commands
    int ofs_end;            // offset to end of file
}
