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
    }
}
