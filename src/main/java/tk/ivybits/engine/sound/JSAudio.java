package tk.ivybits.engine.sound;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;
import java.awt.*;
import java.io.IOException;

/**
 *
 * @author Tudor
 */
public class JSAudio implements IAudio {

    private Clip clip;
    private boolean loop;

    JSAudio(AudioInputStream as) throws LineUnavailableException, IOException {
        Mixer mix = AudioIO.findMixer(as.getFormat());
        clip = (Clip) (mix != null ? mix.getLine(new Line.Info(Clip.class)) : AudioSystem.getLine(new Line.Info(Clip.class)));
        clip.open(as);
        clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (loop && event.getType() == Type.STOP) {
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            clip.start();
                        }
                    });
                }
            }
        });
    }

    public void start() {
        clip.start();
    }

    public void pause() {
        clip.stop();
    }

    public void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }

    public State getState() {
        return clip.isRunning() ? State.PLAYING : (clip.getFramePosition() == 0 ? State.STOPPED : State.PAUSED);
    }

    public void setVolume(float volume) {
        ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(volume);
    }

    public float getVolume() {
        return clip.getLevel();
    }

    public void setLooping(boolean flag) {
        loop = flag;
    }

    public boolean isLooping() {
        return loop;
    }

    public void setMillisecondPosition(long position) {
        clip.setMicrosecondPosition(position / 1000);
    }

    public long getMillisecondPosition() {
        return clip.getMicrosecondPosition() * 1000;
    }

    public long getMillisecondLength() {
        return clip.getMicrosecondLength() * 1000;
    }

    public AudioFormat getFormat() {
        return clip.getFormat();
    }
    
    public Clip getClip() {
        return clip;
    }
}
