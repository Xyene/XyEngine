package tk.ivybits.engine.io;

import org.lwjgl.opengl.OpenGLException;

import java.io.*;

public class IO {
    public static String readString(InputStream in) throws IOException {
        StringBuilder source = new StringBuilder();
        BufferedReader reader = null;
        reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
        String line;
        while ((line = reader.readLine()) != null) {
            source.append(line).append('\n');
        }
        return source.toString();
    }
}
