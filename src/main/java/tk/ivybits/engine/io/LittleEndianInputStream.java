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

package tk.ivybits.engine.io;

import java.io.*;

public class LittleEndianInputStream extends FilterInputStream implements DataInput {

    private DataInputStream din;

    private final byte[] buffer = new byte[8];

    public LittleEndianInputStream(InputStream in) {
        super(in);
        din = new DataInputStream(in);
    }

    public void readFully(byte[] b) throws IOException {
        din.readFully(b);
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        din.readFully(b, off, len);
    }

    public int skipBytes(int n) throws IOException {
        return din.skipBytes(n);
    }

    public boolean readBoolean() throws IOException {
        return din.readBoolean();
    }

    public byte readByte() throws IOException {
        return din.readByte();
    }

    public int readUnsignedByte() throws IOException {
        return din.readUnsignedByte();
    }

    public short readShort() throws IOException {
        din.readFully(buffer, 0, 2);
        return (short) ((buffer[1] << 8) | (buffer[0] & 0xff));
    }

    public int readUnsignedShort() throws IOException {
        din.readFully(buffer, 0, 2);
        return ((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
    }

    public char readChar() throws IOException {
        din.readFully(buffer, 0, 2);
        return (char) ((buffer[1] & 0xff) << 8 | (buffer[0] & 0xff));
    }

    public int readInt() throws IOException {
        din.readFully(buffer, 0, 4);
        return (buffer[3]) << 24 | (buffer[2] & 0xff) << 16 |
                (buffer[1] & 0xff) << 8 | (buffer[0] & 0xff);

    }

    public long readLong() throws IOException {
        din.readFully(buffer, 0, 8);
        return (long) (buffer[7]) << 56 | (long) (buffer[6] & 0xff) << 48 |
                (long) (buffer[5] & 0xff) << 40 | (long) (buffer[4] & 0xff) << 32 |
                (long) (buffer[3] & 0xff) << 24 | (long) (buffer[2] & 0xff) << 16 |
                (long) (buffer[1] & 0xff) << 8 | (long) (buffer[0] & 0xff);

    }

    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public final String readLine() throws IOException {
        return din.readLine();
    }

    public String readUTF() throws IOException {
        return din.readUTF();
    }
}
