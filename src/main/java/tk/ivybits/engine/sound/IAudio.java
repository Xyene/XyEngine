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

import javax.sound.sampled.AudioFormat;

public interface IAudio {

    public enum State {

        PLAYING, PAUSED, STOPPED
    }

    void start();

    void pause();

    void stop();

    State getState();

    void setVolume(float volume);

    float getVolume();

    void setLooping(boolean flag);

    boolean isLooping();

    void setMillisecondPosition(long position);

    long getMillisecondPosition();

    long getMillisecondLength();

    AudioFormat getFormat();
}