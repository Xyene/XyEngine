package tk.ivybits.engine.util;

import org.lwjgl.Sys;

public class FrameTimer {
    private long lastFrame;
    private long lastFPS;
    private int fps;
    private int frames;

    public int getLastDelta() {
        return lastDelta;
    }

    private int lastDelta;

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
        lastDelta = delta;

        return delta;
    }

    public void update() {
        if (getTime() - lastFPS > 1000) {
            fps = frames;
            frames = 0; //reset the frames counter
            lastFPS += 1000; //add one second
        }
        frames++;
    }
}