package tk.ivybits.engine.io.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResource implements IResource {
    private final File file;

    public FileResource(File file) {
        this.file = file;
    }

    @Override
    public String name() {
        return file.toString();
    }

    @Override
    public InputStream openStream() throws IOException {
        return new FileInputStream(file);
    }
}
