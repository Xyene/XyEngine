package sandbox;

import tk.ivybits.engine.io.model.ModelIO;
import tk.ivybits.engine.scene.model.Model;

import java.io.IOException;

public class MD2Test {
    public static void main(String[] argv) throws IOException {
        Model m = ModelIO.readSystem("tk/ivybits/engine/game/model/md2/tris.md2");
    }
}
