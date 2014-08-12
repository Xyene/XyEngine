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

import tk.ivybits.engine.io.res.IResourceFinder;
import tk.ivybits.engine.scene.model.Model;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

public class ModelIO {
    private static final ServiceLoader<IModelReader> sReaders = ServiceLoader.load(IModelReader.class);

    public static String[] getSupportedReadFormats() {
        Set<String> formats = new HashSet<>();
        for (IModelReader decoder : sReaders)
            formats.add(decoder.getFileType());
        String[] keys = new String[formats.size()];
        formats.toArray(keys);
        return keys;
    }

    public static Model read(String path, IResourceFinder finder) throws IOException {
        String suffix = path;
        suffix = suffix.substring(suffix.lastIndexOf(".") + 1).toUpperCase();
        for (IModelReader decoder : sReaders)
            if (decoder.getFileType().equals(suffix)) {
                return new StreamedModel(path, finder);
            }
        throw new IllegalArgumentException("unsupported format: " + suffix);
    }

    static Model _read(String path, IResourceFinder finder) throws IOException {
        String suffix = path;
        suffix = suffix.substring(suffix.lastIndexOf(".") + 1).toUpperCase();
        for (IModelReader decoder : sReaders)
            if (decoder.getFileType().equals(suffix))
                return decoder.load(path, finder);
        throw new IllegalArgumentException("unsupported format: " + suffix);
    }
}
