package tk.ivybits.engine.gl;

import org.lwjgl.opengl.Display;

import static tk.ivybits.engine.gl.GL.*;

public class ImmediateProjection {
    public static void toOrthographicProjection() {
        toOrthographicProjection(0, 0, Display.getWidth(), Display.getHeight());
    }

    public static void toOrthographicProjection(int x, int y, int width, int height) {
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(x, width, y, height, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
    }

    public static void toFrustrumProjection() {
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
        glPopMatrix();
        glPopAttrib();
    }
}
