package tk.ivybits.engine.io.res;

import java.io.IOException;
import java.io.InputStream;

public interface IResource {
    String name();

    InputStream openStream() throws IOException;
}
