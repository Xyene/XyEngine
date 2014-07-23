package tk.ivybits.engine.util;

import java.io.*;
import java.net.URL;

/**
 * @author Tudor
 */
public final class Natives {
    public static enum Platform {

        LINUX, WINDOWS, MAC;

        public static Platform getPlatform() {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                return WINDOWS;
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                return LINUX;
            } else if (os.contains("mac")) {
                return MAC;
            }
            return null;
        }
    }

    public static boolean is64Bit() {
        String osArch = System.getProperty("os.arch");
        return "amd64".equals(osArch) || "x86_64".equals(osArch);
    }

    private static final boolean DEV = false;
    private static final String REV = "6";
    private static final String NATIVE_DIR = "tk/ivybits/engine/natives/";
    private static final String WIN_DIR = NATIVE_DIR + "windows/";
    private static final String NIX_DIR = NATIVE_DIR + "linux/";
    private static final String MAC_DIR = NATIVE_DIR + "mac/";
    private static final String CACHE_DIR = System.getProperty("java.io.tmpdir") + File.separatorChar
            + "lwjglcache_1.0.0_" + (DEV ? System.currentTimeMillis() : REV);

    public static void unpack() {
        System.setProperty("org.lwjgl.librarypath", CACHE_DIR);

        boolean is64Bit = is64Bit();
        switch (Platform.getPlatform()) {
            case WINDOWS:
                if (is64Bit) {
                    unpack(WIN_DIR + "OpenAL64.dll");
                    unpack(WIN_DIR + "lwjgl64.dll");
                } else {
                    unpack(WIN_DIR + "OpenAL32.dll");
                    unpack(WIN_DIR + "lwjgl.dll");
                }
                break;
            case LINUX:
                if (is64Bit) {
                    unpack(NIX_DIR + "libopenal64.so");
                    unpack(NIX_DIR + "liblwjgl64.so");
                } else {
                    unpack(NIX_DIR + "libopenal.so");
                    unpack(NIX_DIR + "liblwjgl.so");
                }
                break;
            case MAC:
                unpack(MAC_DIR + "liblwjgl.jnilib");
                unpack(MAC_DIR + "libopenal.dylib");
                break;
            default:
                throw new UnsupportedOperationException("unsupported platform");
        }
    }

    private static void unpack(String path) {
        try {
            URL url = ClassLoader.getSystemResource(path);

            File pathDir = new File(CACHE_DIR);
            pathDir.mkdirs();
            File libfile = new File(pathDir, path.substring(path.lastIndexOf("/"), path.length()));

            System.out.println("> " + libfile);

            if (!libfile.exists()) {
                libfile.deleteOnExit();
                InputStream in = url.openStream();
                OutputStream out = new BufferedOutputStream(new FileOutputStream(libfile));

                int len;
                byte[] buffer = new byte[8192];
                while ((len = in.read(buffer)) > -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.close();
                in.close();
            }
        } catch (IOException x) {
            throw new RuntimeException("could not unpack binaries", x);
        }
    }
}
