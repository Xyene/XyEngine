package tk.ivybits.engine.scene.geometry;

import tk.ivybits.engine.scene.IDrawContext;
import tk.ivybits.engine.scene.IDrawable;

import static java.lang.Math.*;
import static tk.ivybits.engine.gl.GL.GL_TRIANGLE_STRIP;

public class Sphere {
    public static IDrawable createSphere(IDrawContext ctx, float radius, int divide, boolean normals, boolean uvs) {
        int flag = 0;
        if (normals) {
            flag |= ITesselator.NORMAL_BUFFER;
        }
        if (uvs) {
            flag |= ITesselator.UV_BUFFER;
        }

        ITesselator tess = ctx.createTesselator(flag, GL_TRIANGLE_STRIP);

        float pi = (float) PI;
        float tau = (float) PI * 2;
        float tau_div = tau / divide;
        float pi_div = pi / divide;
        for (int i = 0; i < divide; ++i) {
            float phi1 = i * tau_div, phi2 = (i + 1) * tau_div;
            for (int j = 0; j <= divide; ++j) {
                float theta = j * pi_div;
                float s = phi2 / tau, t = theta / pi;
                float dx = (float) (sin(theta) * cos(phi2));
                float dy = (float) (sin(theta) * sin(phi2));
                float dz = (float) cos(theta);
                tess.pushVertex(radius * dx, radius * dy, radius * dz);
                tess.pushNormal(dx, dy, dz);
                tess.pushTexCoord(s, t);
                s = phi1 / tau;
                dx = (float) (sin(theta) * cos(phi1));
                dy = (float) (sin(theta) * sin(phi1));
                tess.pushVertex(radius * dx, radius * dy, radius * dz);
                tess.pushNormal(dx, dy, dz);
                tess.pushTexCoord(s, t);
            }
        }

        return tess.createDrawable();
    }
}