package top.someapp.dukelib.encode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author zwz
 * Created on 2020-11-19
 */
public class Base36Test {

    @Test
    public void testFromHex() {
        String hex = "0xff"; // 255 = 7 * 36 + 3 = 73(36)
        Base36 base36 = Base36.fromHex(hex);
        System.out.println(base36);
        assertEquals("ff", base36.toHexString());
    }

    @Test
    public void testOf() {
        Base36 base36 = Base36.of("$_"); // 10 = 36
        System.out.println(base36.toDecimalString());
        assertEquals("36", base36.toDecimalString());

        String str = "This is a long message!";
        byte[] bytes = str.getBytes();
        System.out.println(Base36.of(bytes));
    }
}
