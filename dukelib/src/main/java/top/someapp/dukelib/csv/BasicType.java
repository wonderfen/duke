package top.someapp.dukelib.csv;

import javax.annotation.Nonnegative;

/**
 * @author zwz
 * Created on 2020-11-19
 */
public enum BasicType {
    /**
     * 空类型，值为null对应的类型
     * 编码：0，别名：N
     */
    NULL(0, 'N'),

    /**
     * 布尔类型：true | false
     * 编码：10，别名：B
     */
    BOOLEAN(10, 'B'), // true | false

    /**
     * 整数类型：byte, short, int, long
     * 编码：20，别名：I
     */
    INTEGER(20, 'I'), // byte, short, int, long

    /**
     * 实数类型：float, double, real ...,
     * 编码：30，别名：R
     */
    REAL(30, 'R'), // float, double, real ...,

    /**
     * 字符串类型
     * 编码：40，别名：S
     */
    STRING(40, 'S'),

    /**
     * 日期类型，仅包含日期，如：2020-02-20
     * 编码：50，别名：D
     */
    DATE(50, 'D'), // date only, eg: 2020-02-20

    /**
     * 时间类型，仅包含时间，如： 20:20:20
     * 编码：60，别名：T
     */
    TIME(60, 'T'), // time only, eg: 20:20:20

    /**
     * 日期时间类型(iso-8601的表示方式)，如：2020-02-20T20:20:20
     * 编码：70，别名：M
     */
    DATE_TIME(70, 'M'), // date and time(iso-8601), eg: 2020-02-20T20:20:20

    /**
     * 使用base64编码表示的二进制数据
     *
     * 编码：80，别名：A
     */
    BASE64(80, 'A'),

    /**
     * 未知的，不确定的类型
     * 编码：90，别名：X
     */
    UNDEFINED(90, 'X'),
    ;

    /**
     * 该类型的编码
     */
    public final int code;

    /**
     * 该类型的名称
     */
    public final char name;

    private static final String names = "NBIRSDTMAX";

    BasicType(int code, char name) {
        this.code = code;
        this.name = name;
    }

    public static BasicType of(@Nonnegative int code) {
        if (code < NULL.code || code > UNDEFINED.code) {
            return UNDEFINED;
        }
        return values()[code / 10];
    }

    public static BasicType of(char name) {
        int index = names.indexOf(name);
        return index >= 0 ? values()[index] : UNDEFINED;
    }
}
