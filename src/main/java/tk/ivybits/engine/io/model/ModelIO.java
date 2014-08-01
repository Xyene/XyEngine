package tk.ivybits.engine.io.model;

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

    public static Model read(File in) throws IOException {
        String suffix = in.getName();
        suffix = suffix.substring(suffix.lastIndexOf(".") + 1).toUpperCase();
        for (IModelReader decoder : sReaders)
            if (decoder.getFileType().equals(suffix))
                return decoder.load(in);
        throw new IllegalArgumentException("unsupported format: " + suffix);
    }

    public static Model readSystem(String in) throws IOException {
        String suffix = in;
        suffix = suffix.substring(suffix.lastIndexOf(".") + 1).toUpperCase();
        for (IModelReader decoder : sReaders)
            if (decoder.getFileType().equals(suffix))
                return decoder.loadSystem(in);
        throw new IllegalArgumentException("unsupported format: " + suffix);
    }
}
