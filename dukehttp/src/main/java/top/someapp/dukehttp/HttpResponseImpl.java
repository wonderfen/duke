package top.someapp.dukehttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zwz
 * Created on 2019-10-15
 */
class HttpResponseImpl implements Http.HttpResponse {

    private int status = 200;
    private String contentType = "text/plain";
    private boolean writeDone = false;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream(512);
    private Http.ResponseRange range;
    private long total;

    @Override
    public int status() {
        return status;
    }

    @Override
    public long contentLength() {
        return total;
    }

    @Override
    public boolean isRangeSupport() {
        return range != null;
    }

    @Override
    public Http.HttpResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public String contentType() {
        return contentType;
    }

    @Override
    public Http.HttpResponse setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public void write(String content) {
        range = null;
        if (writeDone) {
            return;
        }
        try {
            out.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDone = true;
    }

    @Override
    public void write(InputStream ins) {
        range = null;
        if (writeDone) {
            return;
        }
        byte[] bytes = new byte[512];
        try {
            for (int len; ; ) {
                len = ins.read(bytes);
                if (len > 0) {
                    out.write(bytes, 0, len);
                } else {
                    break;
                }
            }
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDone = true;
    }

    @Override
    public void writeRange(Http.ResponseRange range) {
        this.range = range;
        this.total = range.total;
        out.reset();
        final byte[] bytes = range.data;
        if (bytes.length == 0 || range.length == 0) {
            writeDone = true;
            return;
        }
        out.write(bytes, 0, range.length);
    }

    @Override
    public Http.ResponseRange getRange() {
        return range;
    }

    @Override
    public byte[] getBytes() {
        if (isRangeSupport()) {
            return out.toByteArray();
        }
        return writeDone && out.size() > 0 ? out.toByteArray() : null;
    }
}