package top.someapp.dukelib;

/**
 * @author zw-zheng
 * Created on 2020-11-18
 */
public class AppException extends RuntimeException {
    public AppException() {
        super("Unknown Exception!");
    }

    public AppException(String message) {
        super(message);
    }
}
