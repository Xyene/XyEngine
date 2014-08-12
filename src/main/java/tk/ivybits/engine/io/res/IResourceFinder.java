package tk.ivybits.engine.io.res;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface IResourceFinder {
    IResource find(String name) throws IOException;

    String getParentOf(String name);

    public static final IResourceFinder RESOURCE_FINDER_FILE = new IResourceFinder() {
        @Override
        public IResource find(String name) throws FileNotFoundException {
            return new FileResource(new File(name));
        }

        @Override
        public String getParentOf(String name) {
            return new File(name).getParent() + File.separator;
        }
    };

    public static final IResourceFinder RESOURCE_FINDER_RESOURCE = new IResourceFinder() {
        @Override
        public IResource find(String name) throws IOException {
            return URLResource.getResource(name);
        }

        @Override
        public String getParentOf(String name) {
            if (name.endsWith("/"))
                name = name.substring(0, name.lastIndexOf("/"));
            name = name.substring(0, name.lastIndexOf("/"));
            return name + "/";
        }
    };

    public static final IResourceFinder RESOURCE_FINDER_SYSTEM_RESOURCE = new IResourceFinder() {
        @Override
        public IResource find(String name) throws IOException {
            return URLResource.getSystemResource(name);
        }

        @Override
        public String getParentOf(String name) {
            if (name.endsWith("/"))
                name = name.substring(0, name.lastIndexOf("/"));
            name = name.substring(0, name.lastIndexOf("/"));
            return name + "/";
        }
    };
}