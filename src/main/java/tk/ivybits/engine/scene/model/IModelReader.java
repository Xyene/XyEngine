package tk.ivybits.engine.scene.model;

import tk.ivybits.engine.scene.model.IGeometry;
import tk.ivybits.engine.scene.model.node.Mesh;

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

    public static final ResourceFinder RESOURCE_FINDER_SYSTEM_RESOURCE = new ResourceFinder() {
        @Override
        public InputStream open(String name) throws IOException {
            return getClass().getResourceAsStream("/" + name);
        }

        @Override
        public String parent(String name) {
            return name + "/../";
        }
    };

    IGeometry load(File in) throws IOException;

    IGeometry loadSystem(String in) throws IOException;

    IGeometry load(String in, ResourceFinder finder) throws IOException;

    String getFileType();
}
