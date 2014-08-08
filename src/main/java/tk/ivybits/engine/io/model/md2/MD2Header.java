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
