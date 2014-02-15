package tk.ivybits.engine.scene.model;

import tk.ivybits.engine.scene.model.IGeometry;

import java.io.File;
import java.io.IOException;

public interface IModelReader {
    IGeometry load(File in) throws IOException;

    String getFileType();
}
