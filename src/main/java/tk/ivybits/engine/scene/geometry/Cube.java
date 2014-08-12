package tk.ivybits.engine.scene.geometry;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static tk.ivybits.engine.gl.GL.*;

public class Cube {
    public static IDrawable createCube(IDrawContext ctx, float width, float height, float length, boolean normals, boolean uvs) {
        int flag = 0;
        if (normals) {
            flag |= ITesselator.NORMAL_ATTR;
        }
        if (uvs) {
            flag |= ITesselator.UV_ATTR;
        }

        ITesselator tess = ctx.createTesselator(flag, GL_TRIANGLE_FAN);

        float x = 0;//-(width / 2);
        float y = 0;//-(height / 2);
        float z = 0;//-(length / 2);

       tess.texture(0, 0); tess.vertex(-1, -1, 1);
       tess.texture(0, 1); tess.vertex(1, -1, 1);
       tess.texture(1, 1); tess.vertex(1, 1, 1);
       tess.texture(1, 0); tess.vertex(-1, 1, 1);

       tess.texture(0, 0); tess.vertex(-1, -1, -1);
       tess.texture(0, 1); tess.vertex(-1, -1, 1);
       tess.texture(1, 1); tess.vertex(-1, 1, 1);
       tess.texture(1, 0); tess.vertex(-1, 1, -1);

       tess.texture(0, 0); tess.vertex(1, -1, -1);
       tess.texture(0, 1); tess.vertex(1, 1, -1);
       tess.texture(1, 1); tess.vertex(1, 1, 1);
       tess.texture(1, 0); tess.vertex(1, -1, 1);

       tess.texture(0, 0); tess.vertex(-1, -1, -1);
       tess.texture(0, 1); tess.vertex(1, -1, -1);
       tess.texture(1, 1); tess.vertex(1, -1, 1);
       tess.texture(1, 0); tess.vertex(-1, -1, 1);

       tess.texture(0, 0); tess.vertex(1, 1, -1);
       tess.texture(0, 1); tess.vertex(-1, 1, -1);
       tess.texture(1, 1); tess.vertex(-1, 1, 1);
       tess.texture(1, 0); tess.vertex(1, 1, 1);

       tess.texture(0, 0); tess.vertex(1, -1, -1);
       tess.texture(0, 1); tess.vertex(-1, -1, -1);
       tess.texture(1, 1); tess.vertex(-1, 1, -1);
       tess.texture(1, 0); tess.vertex(1, 1, -1);

        return tess.create();
    }
}
