package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void testHexToByte() {
        byte[] expected = new byte[]{1, -82, 34};
        byte[] actual = Util.hexToByte("01aE22");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testHexToByteThrows() {
        assertThrows(IllegalArgumentException.class, () -> Util.hexToByte("01aE22a"));
    }

    @Test
    public void testByteToHex() {
        String expected = "01ae22";
        String actual = Util.byteToHex(new byte[]{1, -82, 34});
        assertEquals(expected, actual);
    }
}
