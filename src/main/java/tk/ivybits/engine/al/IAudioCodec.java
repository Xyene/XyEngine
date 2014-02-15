package tk.ivybits.engine.al;

import java.io.InputStream;

/**
 *
 * @author Tudor
 */
public interface IAudioCodec {

    IAudio readAudio(InputStream in) throws Exception;

}
