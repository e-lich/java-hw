package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class DictionaryTest {

    @Test
    public void testNullKey() {
        Dictionary<String, Number> dictionary = new Dictionary<>();

        assertThrows(NullPointerException.class, () -> dictionary.put(null, 3));
    }

    @Test
    public void testNoOverwrittenEntry() {
        Dictionary<String, Number> dictionary = new Dictionary<>();

        dictionary.put("Milena", 4);

        assertEquals(4, dictionary.get("Milena"));
    }

    @Test
    public void testOverwrittenEntry() {
        Dictionary<String, Number> dictionary = new Dictionary<>();

        dictionary.put("Milena", 4);
        dictionary.put("Milena", 5);

        assertEquals(5, dictionary.get("Milena"));
    }

    @Test
    public void testKeyDoesNotExist() {
        Dictionary<String, Number> dictionary = new Dictionary<>();

        dictionary.put("Milena", 4);

        assertNull(dictionary.get("Ivana"));
    }

    @Test
    public void testRemoveEntryKeyExists() {
        Dictionary<String, Number> dictionary = new Dictionary<>();

        dictionary.put("Milena", 4);

        assertTrue(Objects.equals(dictionary.remove("Milena"), 4) && dictionary.get("Milena") == null);
    }

    @Test
    public void testRemoveEntryKeyDoesNotExist() {
        Dictionary<String, Number> dictionary = new Dictionary<>();

        dictionary.put("Milena", 4);

        assertNull(dictionary.remove("Ivana"));
    }
}
