package top.someapp.dukehttp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import top.someapp.dukehttp.Http.HttpRequestHandler;
import top.someapp.dukehttp.Http.HttpServer;
import top.someapp.dukehttp.NanoHTTPD.IHTTPSession;
import top.someapp.dukehttp.NanoHTTPD.Response;

import static top.someapp.dukehttp.NanoHTTPD.newChunkedResponse;
import static top.someapp.dukehttp.NanoHTTPD.newFixedLengthResponse;

/**
 * @author zw-zheng
 * Created on 2020-11-19
 */
public class HttpServerLite implements Http.HttpServer {

    private static HttpServerLite sInstance;
    private final NanoHTTPD httpd;
    private final Set<HttpRequestHandler> handlerSet = new HashSet<>();

    private HttpServerLite(int port) {
        this.httpd = new NanoHTTPD(port) {
            @Override
            public Response serve(IHTTPSession session) {
                return HttpServerLite.this.serve(session);
            }
        };
    }

    public static HttpServerLite getInstance(int port) {
        if (sInstance == null) {
            sInstance = new HttpServerLite(port);
        }
        return sInstance;
    }

    protected Response serve(IHTTPSession session) {
        Http.HttpRequest request = new HttpRequestImpl(session);
        Http.HttpResponse response = new HttpResponseImpl();
        boolean hasHandler = false;
        for (Http.HttpRequestHandler h : handlerSet) {
            if (h.handle(request, response)) {
                hasHandler = true;
                break;
            }
        }
        if (hasHandler) {
            if (response.isRangeSupport()) {
                byte[] bytes = response.getBytes();
                Response httpResp = newFixedLengthResponse(
                    Response.Status.lookup(response.status()),
                    response.contentType(), new ByteArrayInputStream(bytes),
                    response.getRange().length);
                httpResp.addHeader("Accept-Ranges", "bytes");
                httpResp.addHeader("Content-Range", response.getRange().toHeader());
                return httpResp;
            }
            return newChunkedResponse(Response.Status.lookup(response.status()),
                                      response.contentType(),
                                      new ByteArrayInputStream(response.getBytes()));
        }
        return newFixedLengthResponse(Response.Status.NOT_FOUND,
                                      "text/html", AbstractRequestHandler.page404Html);
    }

    @Override
    public HttpServer startup() {
        if (!isStarted()) {
            try {
                httpd.start(10 * 1000, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public int port() {
        return httpd.getListeningPort();
    }

    @Override
    public void stop() {
        httpd.stop();
    }

    @Override
    public boolean isStarted() {
        return httpd.wasStarted();
    }

    @Override
    public void registerHandler(@Nonnull HttpRequestHandler... handlers) {
        handlerSet.addAll(Arrays.asList(handlers));
    }
}
