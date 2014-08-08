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

package tk.ivybits.engine.sound;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;
import java.awt.*;
import java.io.IOException;

/**
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
