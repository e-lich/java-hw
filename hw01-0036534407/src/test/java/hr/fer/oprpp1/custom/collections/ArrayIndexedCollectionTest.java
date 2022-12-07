package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ArrayIndexedCollectionTest {

    @Test
    public void testNullCollectionAndInitialCapacityConstructor() {
        assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 5));
    }

    @Test
    public void testCollectionAndInitialCapacityConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(4);
        collection.add(2);
        collection.add(3);

        ArrayIndexedCollection arrayIndexedCollection = new ArrayIndexedCollection(collection, 1);
        assertTrue(Arrays.equals(new Object[] {2, 3}, arrayIndexedCollection.getElements()) && collection.size() == arrayIndexedCollection.size());
    }

    @Test
    public void testTooSmallInitialCapacityConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-1));
    }

    @Test
    public void testDefaultConstructor() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertTrue(Arrays.equals(new Object[] {null, null, null, null,
                                                null, null, null, null,
                                                null, null, null, null,
                                                null, null, null, null}, collection.getElements())
                && collection.size() == 0);
    }

    @Test
    public void testGetElements() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
        collection.add(2);
        collection.add(3);

        assertArrayEquals(new Object[] {2, 3, null}, collection.getElements());
    }

    @Test
    public void testIsEmptyTrue() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(3);

        assertTrue(collection.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
        collection.add(2);
        collection.add(3);

        assertFalse(collection.isEmpty());
    }

    @Test
    public void testDoesContain() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(2);
        assertTrue(collection.contains(2));
    }

    @Test
    public void testDoesNotContain() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        assertFalse(collection.contains(3) || collection.contains(null));
    }

    @Test
    public void testRemoveByIndexTooSmall() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(-1));
    }

    @Test
    public void testRemoveByIndexTooBig() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.removeByIndex(2));
    }

    @Test
    public void testRemoveByIndex() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(2);
        collection.add(3);

        collection.removeByIndex(1);

        assertTrue(collection.size() == 1 && Arrays.equals(new Object[] {2}, collection.toArray()));
    }

    @Test
    public void testRemoveObjectDoesNotExist() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertFalse(collection.remove(5));
    }

    @Test
    public void testRemoveObjectExists() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(2);
        collection.add(3);

        assertTrue(collection.remove(2));
    }

    @Test
    public void testToArray() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
        collection.add(2);
        collection.add(3);
        collection.add(4);
        assertArrayEquals(new Object[] {2, 3, 4}, collection.toArray());
    }

    @Test
    public void testAddNullValue() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Test
    public void testAddRegular() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(2);
        assertArrayEquals(new Object[] {2, null}, collection.getElements());
    }

    @Test
    public void testAddDoubleSize() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(3);
        collection.add(4);
        collection.add(5);
        assertArrayEquals(new Object[] {3, 4, 5, null}, collection.getElements());
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

        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(3);
        collection.add(5);

        collection.forEach(tp);

        assertEquals(tp.sum, 8);
    }

    @Test
    public void testAddAll() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        ArrayIndexedCollection other = new ArrayIndexedCollection();

        collection.add(2);
        collection.add(3);

        other.add(4);
        other.add(5);

        collection.addAll(other);

        assertArrayEquals(new Object[] {2, 3, 4, 5}, collection.toArray());
    }

    @Test
    public void testSize() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(1);
        assertEquals(1, collection.size());
    }

    @Test
    public void testClear() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(3);
        collection.add(5);

        collection.clear();

        assertTrue(collection.size() == 0 && Arrays.equals(new Object[] {null, null}, collection.getElements()));
    }

    @Test
    public void testGetIndexTooSmall() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
    }

    @Test
    public void testGetIndexTooBig() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(3);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(1));
    }

    @Test
    public void testGet() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(3);
        collection.add(4);
        collection.add(5);

        assertEquals(4, collection.get(1));
    }

    @Test
    public void testIndexOfExists() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(3);
        collection.add(4);
        collection.add(5);

        assertEquals(1, collection.indexOf(4));
    }

    @Test
    public void testIndexOfDoesNotExist() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertEquals(-1, collection.indexOf(null));
    }

    @Test
    public void testInsertIndexTooSmall() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(5,-1));
    }

    @Test
    public void testInsertIndexTooBig() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection();
        collection.add(2);

        assertThrows(IndexOutOfBoundsException.class, () -> collection.insert(5,2));
    }

    @Test
    public void testInsertToEnd() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(2);
        collection.add(3);

        collection.insert(4, 2);

        assertArrayEquals(new Object[] {2, 3, 4, null}, collection.getElements());
    }

    @Test
    public void testInsertExtend() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
        collection.add(2);
        collection.add(4);

        collection.insert(3,1);

        assertArrayEquals(new Object[] {2, 3, 4, null}, collection.getElements());
    }

    @Test
    public void testInsert() {
        ArrayIndexedCollection collection = new ArrayIndexedCollection(3);
        collection.add(2);
        collection.add(4);

        collection.insert(3,1);

        assertArrayEquals(new Object[] {2, 3, 4}, collection.getElements());
    }
}
