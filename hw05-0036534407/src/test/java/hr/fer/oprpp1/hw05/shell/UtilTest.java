package hr.fer.oprpp1.hw05.shell;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    public void testGetSimpleCommandArgs() {
        String[] args = Util.getSimpleCommandArgs("arg1 arg2 arg3");
        assertEquals(3, args.length);
        assertEquals("arg1", args[0]);
        assertEquals("arg2", args[1]);
        assertEquals("arg3", args[2]);
    }

    @Test
    public void testGetPathArgs() {
        String[] args = Util.getPathArgs("arg1 \"arg2 arg3\" arg4").toArray(new String[0]);
        assertEquals(3, args.length);
        assertEquals("arg1", args[0]);
        assertEquals("arg2 arg3", args[1]);
        assertEquals("arg4", args[2]);
    }

    @Test
    public void testGetPathArgs2() {
        String[] args = Util.getPathArgs("arg1 \"arg2 \\\"arg3\\\"\" arg4").toArray(new String[0]);
        assertEquals(3, args.length);
        assertEquals("arg1", args[0]);
        assertEquals("arg2 \"arg3\"", args[1]);
        assertEquals("arg4", args[2]);
    }

    @Test
    public void testGetPathArgs3() {
        String[] args = Util.getPathArgs("arg1 \"arg2 \\\\arg3\" arg4").toArray(new String[0]);
        assertEquals(3, args.length);
        assertEquals("arg1", args[0]);
        assertEquals("arg2 \\arg3", args[1]);
        assertEquals("arg4", args[2]);
    }
}
