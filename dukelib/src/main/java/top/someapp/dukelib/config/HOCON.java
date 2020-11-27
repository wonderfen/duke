package top.someapp.dukelib.config;

import java.util.List;

/**
 * HOCON风格的配置信息
 *
 * @author zw-zheng
 * Created on 2020-11-27
 * @see <a href="https://docs.spongepowered.org/5.1.0/zh-CN/server/getting-started/configuration/hocon.html">HOCON
 * 简介</a>
 */
public class HOCON extends BaseConfig {

    protected HOCON(ConfigSource source) {
        super(source);
    }

    @Override protected void load(ConfigSource source) {

    }

    @Override public boolean hasItem(String name) {
        return false;
    }

    @Override public String getString(String name, String defaultValue) {
        return null;
    }

    @Override public int getInt(String name, String defaultValue) {
        return 0;
    }

    @Override public double getDouble(String name, String defaultValue) {
        return 0;
    }

    @Override public <T> T getObject(String name, T defaultValue) {
        return null;
    }

    @Override public <T> List<T> getArray(String name, List<T> defaultValue) {
        return null;
    }
}
