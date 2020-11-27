package top.someapp.dukehttp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * @author zwz
 * Created on 2019-10-15
 */
public interface Http {

    enum Method {
        GET,
        PUT,
        POST,
        DELETE,
        ;
    }

    interface HttpServer {

        HttpServer startup();

        int port();

        void stop();

        boolean isStarted();

        void registerHandler(@Nonnull HttpRequestHandler... handlers);
    }

    interface HttpRequest {

        Method method();

        String uri();

        String queryParameters();

        Map<String, List<String>> parameters();

        List<String> parameterValues(String name);

        String parameter(String name);

        String remoteAddress();

        String header(String name);

        boolean hasMultipart(); // 一般用作是否有文件上传的标示

        Map<String, String> bodyParams();
    }

    interface HttpResponse {

        int status();

        long contentLength();

        boolean isRangeSupport();

        HttpResponse setStatus(int status);

        String contentType();

        HttpResponse setContentType(String contentType);

        void write(String content);

        void write(InputStream ins);

        void writeRange(ResponseRange range);

        ResponseRange getRange();

        byte[] getBytes();
    }

    interface HttpRequestHandler {

        boolean handle(HttpRequest request, HttpResponse response);
    }

    interface ResponseHandler {

        void handle(boolean success, InputStream ins, IOException error);
    }

    interface HttpClient {

        String get(String path) throws IOException;

        String get(String path, Map<String, String> params) throws IOException;

        Map<String, Object> getJSON(String path) throws IOException;

        Map<String, Object> getJSON(String path, Map<String, String> params) throws IOException;

        void download(String path, Map<String, String> params, @Nonnull ResponseHandler handler);

        void getAsync(String path, Map<String, String> params, @Nonnull ResponseHandler handler);

        String post(String path) throws IOException;

        String post(String path, Map<String, String> params) throws IOException;

        Map<String, Object> postJSON(String path, Map<String, String> params) throws IOException;

        void uploadFile(String path, @Nonnull FileUploadEntry entry,
            @Nonnull ResponseHandler handler) throws IOException;
    }

    final class ResponseRange {

        public final byte[] data;
        public final int start;
        public final int length;
        public final long total;

        public ResponseRange(byte[] data, int start, int length, long total) {
            this.data = data;
            this.start = start;
            this.length = length;
            this.total = total;
        }

        public String toHeader() {
            // Content-Range: bytes 0-499/22400 0－499 是指当前发送的数据的范围，而 22400 则是文件的总大小
            return String.format(Locale.US, "bytes %d-%d/%d", start, start + length - 1, total);
        }
    }

    class FileUploadEntry {

        public final File file;
        public final String fileFieldName;  // type="file"的input字段的name
        public final String optionFilenameFiled; // 可选的在body中表示文件名的字段，在上传的文件的文件名中有特殊字符时使用，方便服务端取文件名
        public final String optionFilename; // 可选的代替源文件名的名字

        public FileUploadEntry(@Nonnull File file) {
            this(file, "fileUpload"); // Fine Uploader(http://fineuploader.com) 默认使用的字段名
        }

        public FileUploadEntry(@Nonnull File file, String fileFieldName) {
            this(file, fileFieldName, null, null);
        }

        public FileUploadEntry(@Nonnull File file, String fileFieldName, String optionFilenameFiled,
            String optionFilename) {
            this.file = file;
            this.fileFieldName = fileFieldName;
            this.optionFilenameFiled = optionFilenameFiled;
            this.optionFilename = optionFilename;
        }

        String filename() {
            //            return Strings.isVisible(optionFilenameFiled) && Strings.isVisible(optionFilename)
            //                    ? optionFilename : file.getName();
            return optionFilename == null ? file.getName() : optionFilename;
        }
    }
}
