package top.someapp.dukehttp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import top.someapp.dukehttp.Http.HttpRequest;
import top.someapp.dukehttp.Http.HttpResponse;
import top.someapp.dukehttp.Http.HttpServer;

import static org.junit.Assert.assertNotNull;

/**
 * @author zw-zheng
 * Created on 2020-11-19
 */
public class HttpServerLiteTest implements Http.HttpRequestHandler {

    private HttpServer server;

    @Before
    public void setUp() throws Exception {
        server = HttpServerLite.getInstance(8080);
        assertNotNull(server);
        server.registerHandler(this);
    }

    @Test
    public void testStartup() {
        server.startup();
        CountDownLatch latch = new CountDownLatch(45);
        new Thread(() -> {
            long count;
            while ((count = latch.getCount()) > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(count + ": Wait server stop.");
                latch.countDown();
            }
            server.stop();
            System.out.println("Server stopped!");
        }).start();
        try {
            latch.await(50, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test finished!");
    }

    @Override
    public boolean handle(HttpRequest request, HttpResponse response) {
        response.write(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return true;
    }
}
