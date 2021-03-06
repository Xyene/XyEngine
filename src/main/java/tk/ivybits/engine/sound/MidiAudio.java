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

import javax.sound.midi.*;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import java.io.InputStream;

/**
 * @author Tudor
 */
public class MidiAudio implements IAudio {

    private Sequencer sequencer;
    private Sequence sequence;
    private boolean loop;
    private float volume;
    private AudioFormat format;

    MidiAudio(InputStream data) throws MidiUnavailableException {
        getSequencer();
        setSequence(data);
        sequencer.addMetaEventListener(new MetaEventListener() {
            public void meta(MetaMessage msg) {
                if (msg.getType() == 47) {
                    try {
                        sequencer.setSequence(sequence);
                    } catch (InvalidMidiDataException e) {
                        e.printStackTrace();
                    }
                    sequencer.setTickPosition(0);
                    if (loop) { // End of track                        
                        sequencer.start();
                    }
                }
            }
        });

        AudioFormat base = new AudioFormat(44100, 16, 2, true, false);
        format = new AudioFileFormat(new Type("MIDI", "mid"), base, (int) (base.getFrameRate() * (sequence.getMicrosecondLength() / 1000000 + 4))).getFormat();
    }

    public void start() {
        sequencer.start();
    }

    public void pause() {
        sequencer.stop();
    }

    public void stop() {
        sequencer.stop();
        sequencer.setTickPosition(0);
    }

    public State getState() {
        return sequencer.isRunning() ? State.PLAYING : (sequencer.getMicrosecondPosition() == 0 ? State.STOPPED : State.PAUSED);
    }

    public void setVolume(float volume) {
        try {
            this.volume = volume;
            Synthesizer synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            MidiChannel[] channels = synthesizer.getChannels();
            for (MidiChannel channel : channels) {
                if (channel != null) {
                    channel.controlChange(7, (int) volume);
                }
            }
        } catch (Exception ignored) {
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setLooping(boolean flag) {
        loop = flag;
    }

    public boolean isLooping() {
        return loop;
    }

    public void setMillisecondPosition(long position) {
        sequencer.setMicrosecondPosition(position * 1000);
    }

    public long getMillisecondPosition() {
        return sequencer.getMicrosecondPosition() / 1000;
    }

    public long getMillisecondLength() {
        return sequencer.getMicrosecondLength() / 1000;
    }

    private void getSequencer() throws MidiUnavailableException {
        try {
            sequencer = MidiSystem.getSequencer();
            if (sequencer != null) {
                try {
                    sequencer.getTransmitter();
                } catch (MidiUnavailableException ignored) {
                }
                sequencer.open();
            }
        } catch (MidiUnavailableException mue) {
            sequencer = null;
            try {
                sequencer = MidiSystem.getSequencer();
                if (sequencer != null) {
                    try {
                        sequencer.getTransmitter();
                    } catch (MidiUnavailableException ignored) {
                    }
                    sequencer.open();
                }
            } catch (Exception ignored) {
                sequencer = null;
            }
        }

        if (sequencer == null) {
            sequencer = openSequencer("Real Time Sequencer");
        }
        if (sequencer == null) {
            sequencer = openSequencer("Java Sound Sequencer");
        }
        if (sequencer == null) {
            throw new MidiUnavailableException("unable to find MIDI-capable device");
        }
    }

    private Sequencer openSequencer(String containsString) {
        Sequencer s;
        s = (Sequencer) openMidiDevice(containsString);
        if (s == null) {
            return null;
        }
        try {
            s.getTransmitter();
        } catch (MidiUnavailableException mue) {
            return null;
        }

        return s;
    }

    private MidiDevice openMidiDevice(String containsString) {
        MidiDevice device;
        MidiDevice.Info[] midiDevices = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info midiDevice : midiDevices) {
            try {
                device = MidiSystem.getMidiDevice(midiDevice);
            } catch (MidiUnavailableException e) {
                device = null;
            }
            if (device != null && midiDevice.getName().contains(containsString)) {
                try {
                    device.open();
                } catch (MidiUnavailableException mue) {
                    device = null;
                }
                return device;
            }
        }
        return null;
    }

    private void setSequence(InputStream midiSource) {
        if (sequencer == null || midiSource == null) {
            return;
        }

        try {
            sequence = MidiSystem.getSequence(midiSource);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (sequence != null) {
            try {
                sequencer.setSequence(sequence);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public AudioFormat getFormat() {
        return format;
    }
}
