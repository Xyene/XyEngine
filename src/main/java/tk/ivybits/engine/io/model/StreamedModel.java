package tk.ivybits.engine.io.model;

import tk.ivybits.engine.io.res.IResourceFinder;
import tk.ivybits.engine.scene.model.Mesh;
import tk.ivybits.engine.scene.model.Model;

import java.io.IOException;
import java.lang.ref.*;
import java.util.*;

class StreamedModel extends Model {
    private final String path;
    private final IResourceFinder finder;
    protected SoftReference<Model> meshes;

    private static final ReferenceQueue<Model> MODEL_REFERENCE_QUEUE = new ReferenceQueue<>();
    private static final HashMap<Reference, String> REFERENCE_NAMES = new HashMap<>();

    static {
        new Thread("Model Cleanup Notification Thread") {
            public void run() {
                while (true) {
                    try {
                        Reference ref = MODEL_REFERENCE_QUEUE.remove();
                        System.out.println("Cleaned up " + REFERENCE_NAMES.get(ref));
                        REFERENCE_NAMES.remove(ref);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public StreamedModel(String path, IResourceFinder finder) {
        this.path = path;
        this.finder = finder;
    }

    @Override
    public List<Mesh> getMeshes() {
        if (meshes == null || meshes.get() == null) {
            try {
                Model model = ModelIO._read(path, finder);
                meshes = new SoftReference<>(model, MODEL_REFERENCE_QUEUE);
                REFERENCE_NAMES.put(meshes, getName());
                return model.getMeshes();
            } catch (IOException e) {
                return Collections.emptyList();
            }
        }
        return meshes.get().getMeshes();
    }

    @Override
    public String getName() {
        return path;
    }
}
