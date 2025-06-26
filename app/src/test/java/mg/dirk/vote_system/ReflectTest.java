package mg.dirk.vote_system;

import static org.junit.Assert.assertEquals;
import static mg.dirk.vote_system.reflect.ReflectUtils.makeFirstUpperCase;

import org.junit.Test;

public class ReflectTest {
    @Test
    public void testMakeFirstUpperCase() {
        assertEquals("Tony", makeFirstUpperCase("tony"));
    }

    @Test
    public void testStringCast() {
        assertEquals(2, Integer.parseInt("2"));
    }
}
