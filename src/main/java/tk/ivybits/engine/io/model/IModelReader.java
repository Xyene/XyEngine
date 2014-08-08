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
