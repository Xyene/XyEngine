package tk.ivybits.engine.sound;

import java.io.InputStream;

/**
 *
 * @author Tudor
 */
public interface IAudioCodec {

    IAudio readAudio(InputStream in) throws Exception;

}
