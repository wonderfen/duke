package top.someapp.dukelib.config;

import java.io.IOException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author zw-zheng
 * Created on 2020-11-27
 */
public class ConfigSourceTest {

    @Test
    public void testRead() throws IOException {
        Config config = ConfigSource.read("sample.conf");
        assertNotNull(config);
    }
}
