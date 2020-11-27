package top.someapp.dukelib.config;

import java.io.OutputStream;
import java.util.List;

/**
 * 配置信息
 *
 * @author zw-zheng
 * Created on 2020-11-27
 */
public interface Config {

    void writeTo(OutputStream out);

    boolean isLoaded();

    boolean hasItem(String name);

    /// get primitives values
    String getString(String name, String defaultValue);

    int getInt(String name, String defaultValue);

    double getDouble(String name, String defaultValue);
    /// ~get primitives values

    <T> T getObject(String name, T defaultValue);

    <T> List<T> getArray(String name, List<T> defaultValue);
}
