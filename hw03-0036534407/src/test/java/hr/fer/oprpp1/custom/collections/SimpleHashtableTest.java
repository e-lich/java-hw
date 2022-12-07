package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleHashtableTest {

    @Test
    public void testDefaultConstructor() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        assertEquals(16, hashtable.getCapacity());
    }

    @Test
    public void testInitialCapacityConstructor() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(5);

        assertEquals(8, hashtable.getCapacity());
    }

    @Test
    public void testIllegalInitialCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<>(-1));
    }

    @Test
    public void testPutNullKey() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(5);

        assertThrows(NullPointerException.class, () -> hashtable.put(null, 4));
    }

    @Test
    public void testPutNewEntry() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(5);

        hashtable.put("Milena", 5);

        assertEquals(5, hashtable.get("Milena"));
    }

    @Test
    public void testOverwriteEntry() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(5);

        hashtable.put("Milena", 4);
        hashtable.put("Milena", 5);

        assertEquals(5, hashtable.get("Milena"));
    }

    @Test
    public void testDoubleTableSize() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Marko", 4);

        assertEquals(4, hashtable.getCapacity());
    }

    @Test
    public void testGetNullKey() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertNull(hashtable.get(null));
    }

    @Test
    public void testGetKeyNotFound() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertNull(hashtable.get("Ante"));
    }

    @Test
    public void testTableSize() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertEquals(2, hashtable.size());
    }

    @Test
    public void testContainsNullKey() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertFalse(hashtable.containsKey(null));
    }

    @Test
    public void testContainsKey() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertTrue(hashtable.containsKey("Milena"));
    }

    @Test
    public void testDoesNotContainKey() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertFalse(hashtable.containsKey("Ante"));
    }

    @Test
    public void testContainsValue() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertTrue(hashtable.containsValue(4));
    }

    @Test
    public void testDoesNotContainValue() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertFalse(hashtable.containsValue(5));
    }

    @Test
    public void testRemoveNullKey() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertNull(hashtable.remove(null));
    }

    @Test
    public void testRemoveKeyNotFound() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertNull(hashtable.remove("Ante"));
    }

    @Test
    public void testRemoveFromBeginningOfSlot() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);

        assertTrue(hashtable.remove("Milena") == 4
                && hashtable.size() == 1
                && !hashtable.containsKey("Milena"));
    }

    @Test
    public void testRemoveFromEndOfSlot() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        assertTrue(hashtable.remove("Kristina") == 5
                && hashtable.size() == 2
                && !hashtable.containsKey("Kristina"));
    }

    @Test
    public void testRemoveFromMiddleOfSlot() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>(2);

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);
        hashtable.put("Jasna", 3);

        assertTrue(hashtable.remove("Kristina") == 5
                && hashtable.size() == 3
                && !hashtable.containsKey("Kristina"));
    }

    @Test
    public void testClearHashtable() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);
        hashtable.put("Jasna", 3);

        hashtable.clear();

        assertTrue(hashtable.getCapacity() == 16 && hashtable.isEmpty());
    }

    @Test
    public void testToString() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        assertEquals(hashtable.toString(), "[Ivana=4, Kristina=5, Milena=4, \b\b]");
    }

    @Test
    public void testHasNextUndetectedModification() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        assertThrows(ConcurrentModificationException.class,
                () -> {
            for (SimpleHashtable.TableEntry<String, Integer> pair : hashtable) {
                if (pair.getKey().equals("Ivana")) {
                    hashtable.remove("Ivana");
                }
            }
        });
    }

    @Test
    public void testNextUndetectedModification() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        assertThrows(ConcurrentModificationException.class,
                () -> {
                    Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = hashtable.iterator();
                    hashtable.remove("Ivana");
                    iter.next();
                });
    }

    @Test
    public void testRemoveUndetectedModification() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        assertThrows(ConcurrentModificationException.class,
                () -> {
                    Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = hashtable.iterator();
                    iter.next();
                    hashtable.remove("Ivana");
                    iter.remove();
                });
    }

    @Test
    public void testNoNextElement() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);

        assertThrows(NoSuchElementException.class,
                () -> {
                    Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = hashtable.iterator();
                    iter.next();
                    iter.next();
                });
    }

    @Test
    public void testIteratorRemove() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = hashtable.iterator();
        while(iter.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
            iter.remove();
        }

        assertEquals(0, hashtable.size());
    }

    @Test
    public void testIteratorAlreadyRemoved() {
        SimpleHashtable<String, Integer> hashtable = new SimpleHashtable<>();

        hashtable.put("Milena", 4);
        hashtable.put("Ivana", 4);
        hashtable.put("Kristina", 5);

        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = hashtable.iterator();
        iter.next();
        iter.remove();

        assertThrows(IllegalStateException.class, iter::remove);
    }
}
