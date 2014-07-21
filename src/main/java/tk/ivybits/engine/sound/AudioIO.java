package tk.ivybits.engine.sound;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.IntBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;

import static org.lwjgl.openal.AL10.*;

public class AudioIO {
    private static final HashMap<String, IAudioCodec> codecs = new HashMap<>();
    private static boolean openALCapable;
    public static final IAudioCodec CODEC_AIF = new IAudioCodec() {
        public IAudio readAudio(InputStream in) throws Exception {
            if (!openALCapable) {
                return CODEC_JAVASOUND.readAudio(in);
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(in);
            AudioFormat af = ais.getFormat();
            int buffer = -1;
            try {
                IntBuffer buf = BufferUtils.createIntBuffer(1);

                AiffDecoder data = AiffDecoder.create(ais);
                alGenBuffers(buf);
                alBufferData(buf.get(0), data.format, data.data, data.samplerate);

                buffer = buf.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (buffer == -1) {
                throw new IOException("unable to load: " + in);
            }

            return new ALAudio(buffer, af);
        }
    };
    public static final IAudioCodec CODEC_WAV = new IAudioCodec() {
        public IAudio readAudio(InputStream in) throws Exception {
            if (!openALCapable) {
                return CODEC_JAVASOUND.readAudio(in);
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(in);
            AudioFormat af = ais.getFormat();

            int buffer = -1;
            try {
                IntBuffer buf = BufferUtils.createIntBuffer(1);

                WaveData data = WaveData.create(ais);
                alGenBuffers(buf);
                alBufferData(buf.get(0),
                        data.format,
                        data.data,
                        data.samplerate);

                buffer = buf.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (buffer == -1) {
                throw new IOException("unable to load: " + in);
            }

            return new ALAudio(buffer, af);
        }
    };
    public static final IAudioCodec CODEC_MIDI = new IAudioCodec() {
        public MidiAudio readAudio(InputStream in) throws Exception {
            return new MidiAudio(in);
        }
    };
    public static final IAudioCodec CODEC_JAVASOUND = new IAudioCodec() {
        public JSAudio readAudio(InputStream in) throws Exception {
            return new JSAudio(AudioSystem.getAudioInputStream(in));
        }
    };

    static {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    AL.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                openALCapable = AL.isCreated();
                return null;
            }
        });
        codecs.put("AIF", CODEC_AIF);
        codecs.put("WAV", CODEC_WAV);
        codecs.put("MIDI", CODEC_MIDI);
        codecs.put("MID", CODEC_MIDI);
    }

    public static IAudio read(File file) throws Exception {
        String fileName = file.getName();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > fileName.lastIndexOf(File.pathSeparator)) {
            extension = fileName.substring(i + 1);
        }

        return read(extension, new BufferedInputStream(new FileInputStream(file)));
    }

    public static IAudio read(String format, InputStream in) throws Exception {
        format = format.toUpperCase();
        if (codecs.containsKey(format)) {
            return codecs.get(format).readAudio(in);
        }

        throw new IOException("unsupported format for audio: " + format);
    }

    public static IAudioCodec getCodec(String format) {
        return codecs.get(format.toUpperCase());
    }

    public static void addCodec(String format, IAudioCodec codec) {
        codecs.put(format.toUpperCase(), codec);
    }

    public static Mixer findMixer(AudioFormat format) {
        DataLine.Info lineInfo = new DataLine.Info(SourceDataLine.class,
                format);
        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        //check each available mixer to see if it is acceptable
        for (Mixer.Info mixerInfo : mixerInfos) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            //first check if it supports our line
            if (!mixer.isLineSupported(lineInfo)) {
                continue; //nope
            }
            //now check if we've used up our lines
            int maxLines = mixer.getMaxLines(lineInfo);
            //if it's not specified, it's supposedly unlimited
            if (maxLines == AudioSystem.NOT_SPECIFIED) {
                return mixer;
            }
            //otherwise we should count them
            int linesOpen = 0;
            Line[] sourceLines = mixer.getSourceLines();
            for (Line sourceLine : sourceLines) {
                //check if it matches our line
                if (sourceLine.getLineInfo().matches(lineInfo)) {
                    linesOpen++; //one line used up
                }
            }
            //now we can see if any are available
            if (maxLines > linesOpen) {
                return mixer;
            }
        }
        //couldn't find one
        return null;
    }

    public static AudioFormat getFormat(InputStream in) throws IOException, UnsupportedAudioFileException {
        return AudioSystem.getAudioInputStream(new BufferedInputStream(in)).getFormat();
    }
}
