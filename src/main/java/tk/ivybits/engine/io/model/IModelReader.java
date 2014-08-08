package tk.ivybits.engine.io.model;

import tk.ivybits.engine.scene.model.Model;

import java.io.*;

public interface IModelReader {
    public static interface ResourceFinder {
        InputStream open(String name) throws IOException;

        String parent(String name);
    }

    public static final ResourceFinder RESOURCE_FINDER_FILE = new ResourceFinder() {
        @Override
        public InputStream open(String name) throws FileNotFoundException {
            return new FileInputStream(new File(name));
        }

        @Override
        public String parent(String name) {
            return new File(name).getParent() + File.separator;
        }
    };

    public static final ResourceFinder RESOURCE_FINDER_PACKAGED_RESOURCE = new ResourceFinder() {
        @Override
        public InputStream open(String name) throws IOException {
            return getClass().getResourceAsStream("/" + name);
        }

        @Override
        public String parent(String name) {
            if (name.endsWith("/"))
                name = name.substring(0, name.lastIndexOf("/"));
            name = name.substring(0, name.lastIndexOf("/"));
            return name + "/";
        }
    };

    Model load(File in) throws IOException;

    Model loadSystem(String in) throws IOException;

    Model load(String in, ResourceFinder finder) throws IOException;

    String getFileType();
}