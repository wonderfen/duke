package top.someapp.dukelib.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * @author zw-zheng
 * Created on 2020-11-27
 */
public final class ConfigSource {

    private final URL url;

    ConfigSource(URL url) {
        this.url = url;
    }

    public static Config read(String path) throws IOException {
        // 1. if path is absolutely
        URL url = null;
        if (path.startsWith("/") || path.contains(":")) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                url = file.toURI().toURL();
            }
        } else { // 2. if path is relative
            // using current dir
            File file = new File(System.getProperty("user.dir", "."), path);
            if (file.exists() && file.isFile()) {
                url = file.toURI().toURL();
            } else { // using classpath
                url = Thread.currentThread().getContextClassLoader().getResource(path);
            }
        }

        if (url == null) throw new IOException("Can not load source read: " + path);
        return new HOCON(new ConfigSource(url));
    }

    public static Config read(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (file.isFile()) return new HOCON(new ConfigSource(file.toURI().toURL()));
        throw new IOException("file can not read!");
    }

    public URL getUrl() {
        return url;
    }
}
