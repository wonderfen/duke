package top.someapp.dukelib.config;

import java.io.OutputStream;

/**
 * @author zw-zheng
 * Created on 2020-11-27
 */
public abstract class BaseConfig implements Config {

    private final ConfigSource source;
    private boolean loaded;

    protected BaseConfig(ConfigSource source) {
        this.source = source;
        load(source);
        loaded = true;
    }

    protected ConfigSource getSource() {
        return source;
    }

    @Override public void writeTo(OutputStream out) {

    }

    @Override public boolean isLoaded() {
        return loaded;
    }

    protected abstract void load(ConfigSource source);
}
