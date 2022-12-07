package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class LinkedListIndexedCollectionTest {                                                                          // TODO: recycling tests without making identical methods part of Collection?
    @Test
    public void testNullCollectionConstructor() {
        assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(null));
    }

    @Test
    public void testCollectionConstructor() {
        LinkedListIndexedCollection other = new LinkedListIndexedCollection();
        other.add(2);
        other.add(3);

        LinkedListIndexedCollection collection = new LinkedListIndexedCollection(other);

        assertTrue(collection.size() == 2 && Arrays.equals(new Object[] {2, 3}, collection.toArray()));
    }

    @Test
    public void testEmptyCollectionConstructor() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertTrue(collection.size() == 0 && Arrays.equals(new Object[] {}, collection.toArray()));
    }

    @Test
    public void testIsEmptyTrue() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertTrue(collection.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertFalse(collection.isEmpty());
    }

    @Test
    public void testDoesContain() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        assertTrue(collection.contains(2));
    }

    @Test
    public void testDoesNotContain() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        assertFalse(collection.contains(3) || collection.contains(null));
    }

    @Test
    public void testRemoveByIndexTooSmall() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(-1));
    }

    @Test
    public void testRemoveByIndexTooBig() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(2));
    }

    @Test
    public void testRemoveByIndexFromBeginning() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        collection.removeByIndex(0);

        assertTrue(collection.size() == 1 && Arrays.equals(new Object[] {3}, collection.toArray()));
    }

    @Test
    public void testRemoveByIndexFromEnd() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        collection.removeByIndex(1);

        assertTrue(collection.size() == 1 && Arrays.equals(new Object[] {2}, collection.toArray()));
    }

    @Test
    public void testRemoveByIndex() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);
        collection.add(4);

        collection.removeByIndex(1);

        assertTrue(collection.size() == 2 && Arrays.equals(new Object[] {2, 4}, collection.toArray()));
    }

    @Test
    public void testRemoveObjectDoesNotExist() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertFalse(collection.remove(5));
    }

    @Test
    public void testRemoveObjectExists() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertTrue(collection.remove(2) && Arrays.equals(new Object[] {3}, collection.toArray()));
    }

    @Test
    public void testAddAndToArray() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);
        collection.add(4);
        assertArrayEquals(new Object[] {2, 3, 4}, collection.toArray());
    }

    @Test
    public void testAddNullValue() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    public void testForEach() {
        class TestProcessor extends Processor {
            public int sum = 0;
            @Override
            public void process(Object value) {
                sum += (int)value;
            }
        }

        TestProcessor tp = new TestProcessor();

        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(3);
        collection.add(5);

        collection.forEach(tp);

        assertEquals(tp.sum, 8);
    }

    @Test
    public void testAddAll() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        LinkedListIndexedCollection other = new LinkedListIndexedCollection();

        collection.add(2);
        collection.add(3);

        other.add(4);
        other.add(5);

        collection.addAll(other);

        assertArrayEquals(new Object[] {2, 3, 4, 5}, collection.toArray());
    }

    @Test
    public void testSize() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(1);
        assertEquals(1, collection.size());
    }

    @Test
    public void testClear() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(3);
        collection.add(5);

        collection.clear();

        assertTrue(collection.size() == 0 && Arrays.equals(new Object[] {}, collection.toArray()));
    }

    @Test
    public void testGetIndexTooSmall() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
    }

    @Test
    public void testGetIndexTooBig() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(3);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(1));
    }

    @Test
    public void testGet() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(3);
        collection.add(4);
        collection.add(5);

        assertEquals(4, collection.get(1));
    }

    @Test
    public void testIndexOfExists() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(3);
        collection.add(4);
        collection.add(5);

        assertEquals(1, collection.indexOf(4));
    }

    @Test
    public void testIndexOfDoesNotExist() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertEquals(-1, collection.indexOf(null));
    }

    @Test
    public void testInsertIndexTooSmall() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(5,-1));
    }

    @Test
    public void testInsertIndexTooBig() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(5,2));
    }

    @Test
    public void testInsertToEnd() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        collection.insert(4, 2);

        assertArrayEquals(new Object[] {2, 3, 4}, collection.toArray());
    }

    @Test
    public void testInsertToBeginning() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(3);

        collection.insert(1,0);

        assertArrayEquals(new Object[] {1, 2, 3}, collection.toArray());
    }

    @Test
    public void testInsert() {
        LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
        collection.add(2);
        collection.add(4);

        collection.insert(3,1);

        assertArrayEquals(new Object[] {2, 3, 4}, collection.toArray());
    }
}
