package top.someapp.dukehttp;

import java.util.regex.Pattern;

/**
 * @author zw-zheng
 * Created on 2020-11-19
 */
public abstract class AbstractRequestHandler implements Http.HttpRequestHandler {

    public static final String defaultHtmlHead = "<!doctype html>\n" +
        "<html lang='zh-cmn-Hans'>\n" +
        "<head>\n" +
        "    <meta charset='UTF-8'>\n" +
        "    <meta name='viewport'\n" +
        "          content='width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0'>\n"
        +
        "    <meta http-equiv='X-UA-Compatible' content='ie=edge'>\n" +
        "    <title>404 Not found</title>\n" +
        "</head>\n";
    public static final String page404Html = defaultHtmlHead +
        "<body>\n" +
        "<h3 style='text-align: center;'>404 Not found</h3>\n" +
        "<hr>\n" +
        "</body>\n" +
        "</html>";
    public static final String page50xHtml = defaultHtmlHead +
        "<body>\n" +
        "<h3 style='text-align: center;'>500 Internal Server Error</h3>\n" +
        "<hr>\n" +
        "</body>\n" +
        "</html>";
    protected static final Pattern fileReg = Pattern.compile("(/\\S+)*/\\S+[.]\\S+");

    @Override
    public boolean handle(Http.HttpRequest request, Http.HttpResponse response) {
        send404(response);
        return true;
    }

    protected boolean isFileRequest(Http.HttpRequest request) {
        return fileReg.matcher(request.uri()).matches();
    }

    protected boolean isFileGet(Http.HttpRequest request) {
        return request.method() == Http.Method.GET && isFileRequest(request);
    }

    public static String contentTypeOf(String fileName) {
        int index = fileName.lastIndexOf('.');
        String suffix = fileName;
        if (index >= 0) {
            suffix = fileName.substring(index);
        }
        switch (suffix.toLowerCase()) {
            // text files
            case ".css":
            case ".html":
                return "text/" + suffix.substring(1);
            case ".js":
                return "application/x-javascript";
            // images
            case ".gif":
                return "image/gif";
            case ".jpg":
            case ".jpeg":
                return "image/jpeg";
            case ".png":
                return "image/png";
            case ".svg":
                return "image/svg+xml";
            // audios
            case ".mid":
            case ".midi":
                return "audio/mid";
            case ".mp3":
                return "audio/mp3";
            // videos
            case ".avi":
                return "video/avi";
            case ".mp4":
                return "video/mpeg4";
        }
        return "application/octet-stream";
    }

    protected void send404(Http.HttpResponse response) {
        response.setContentType("text/html").setStatus(404).write(page404Html);
    }

    protected void send50x(Http.HttpResponse response) {
        response.setContentType("text/html").setStatus(500).write(page50xHtml);
    }
}