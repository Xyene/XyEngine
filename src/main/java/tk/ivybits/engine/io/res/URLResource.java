package tk.ivybits.engine.io.res;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLResource implements IResource {
    private final URL url;

    public URLResource(URL url) {
        this.url = url;
    }

    public static URLResource getResource(String path) {
        return new URLResource(URLResource.class.getResource(path));
    }

    public static URLResource getSystemResource(String path) {
        return new URLResource(ClassLoader.getSystemResource(path));
    }

    @Override
    public String name() {
        return url.toString();
    }

    @Override
    public InputStream openStream() throws IOException {
        return url.openStream();
    }
}
