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

package tk.ivybits.engine.util;

import org.lwjgl.Sys;

public class FrameTimer {
    private long lastFrame;
    private long lastFPS;
    private int fps;
    private int frames;

    public void start() {
        getDelta();
        lastFPS = getTime();
    }

    public int fps() {
        return fps;
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
        return delta;
    }

    public void update() {
        if (getTime() - lastFPS > 1000) {
            fps = frames;
            frames = 0;
            lastFPS += 1000;
        }
        frames++;
    }
}