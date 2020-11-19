package top.someapp.dukelib.encode;

import java.math.BigInteger;
import java.util.Objects;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import top.someapp.dukelib.AppException;

/**
 * 数字与36进制相互转化的工具类，将0、1使用_、$代替
 *
 * @author zw-zheng
 * Created on 2020-11-18
 */
public class Base36 {

    private final BigInteger bigInt;
    private static final char[] DICTIONARY = "_$23456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int BASE = 36;
    private String text;

    Base36(BigInteger bigInt) {
        this.bigInt = bigInt;
    }

    public static Base36 of(@Nonnull String text) {
        char[] chars = text.toCharArray();
        BigInteger bigInt = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(BASE);
        for (char ch : chars) {
            int index = -1;
            if (ch == '_') {
                index = 0;
            } else if (ch == '$') {
                index = 1;
            } else if (ch >= '2' && ch <= '9') {
                index = 2 + (ch - '2');
            } else if (ch >= 'a' && ch <= 'z') {
                index = 10 + (ch - 'a');
            } else if (ch >= 'A' && ch <= 'Z') {
                index = 10 + (ch - 'A');
            } else {
                new AppException("Invalid char: " + ch);
            }
            bigInt = bigInt.multiply(base).add(BigInteger.valueOf(index));
        }
        return new Base36(bigInt);
    }

    public static Base36 of(@Nonnull byte[] bytes) {
        return new Base36(new BigInteger(bytes));
    }

    public static Base36 fromHex(@Nonnull String hex) {
        if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        }
        return new Base36(new BigInteger(hex, 16));
    }

    public static Base36 from(@Nonnegative int val) {
        if (val < 0) {
            throw new AppException("Base36 not accept negative number(" + val + ")!!");
        }
        return new Base36(BigInteger.valueOf(val));
    }

    public static Base36 from(@Nonnegative long val) {
        if (val < 0) {
            throw new AppException("Base36 not accept negative number(" + val + ")!!");
        }
        return new Base36(BigInteger.valueOf(val));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base36 base36 = (Base36) o;
        return Objects.equals(bigInt, base36.bigInt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bigInt);
    }

    @Override
    public String toString() {
        if (text == null) {
            StringBuilder content = new StringBuilder();
            char[] chars = bigInt.toString(BASE).toCharArray();
            for (char ch : chars) {
                if (ch == '0') {
                    content.append('_');
                } else if (ch == '1') {
                    content.append('$');
                } else {
                    content.append(ch);
                }
            }
            text = content.toString();
        }
        return text;
    }

    public String toString(@Nonnegative int radix) {
        return bigInt.toString(radix);
    }

    public String toHexString() {
        return toString(16);
    }

    public String toDecimalString() {
        return toString(10);
    }

    public String toBinaryString() {
        return toString(2);
    }
}
