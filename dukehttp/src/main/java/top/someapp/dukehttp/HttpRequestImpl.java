package top.someapp.dukehttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zwz
 * Created on 2019-10-15
 */
class HttpRequestImpl implements Http.HttpRequest {
    private final NanoHTTPD.IHTTPSession session;
    private Map<String, String> header;
    private Map<String, String> body;

    HttpRequestImpl(NanoHTTPD.IHTTPSession session) {
        this.session = session;
    }

    @Override
    public Http.Method method() {
        switch (session.getMethod()) {
            case GET:
            case PUT:
            case POST:
            case DELETE:
                return Http.Method.valueOf(session.getMethod().name());
        }
        return null;
    }

    @Override
    public String uri() {
        return session.getUri();
    }

    @Override
    public String queryParameters() {
        return session.getQueryParameterString();
    }

    @Override
    public Map<String, List<String>> parameters() {
        return session.getParameters();
    }

    @Override
    public List<String> parameterValues(String name) {
        return parameters().get(name);
    }

    @Override
    public String parameter(String name) {
        List<String> values = parameterValues(name);
        return values == null || values.isEmpty() ? null : parameterValues(name).get(0);
    }

    @Override
    public String remoteAddress() {
        return session.getRemoteIpAddress();
    }

    @Override
    public String header(String name) {
        if (header == null) {
            header = session.getHeaders();
        }
        return header == null ? null : header.get(name);
    }

    @Override
    public boolean hasMultipart() {
        String contentType = header("content-type");
        return contentType != null && contentType.contains("multipart/");
    }

    @Override
    public Map<String, String> bodyParams() {
        if (body == null) {
            body = new HashMap<>();
        }
        if (body.isEmpty()) {
            try {
                session.parseBody(body);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NanoHTTPD.ResponseException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}