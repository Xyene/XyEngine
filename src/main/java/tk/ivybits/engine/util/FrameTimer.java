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