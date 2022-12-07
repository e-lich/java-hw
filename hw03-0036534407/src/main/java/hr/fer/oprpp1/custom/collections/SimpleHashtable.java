package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;

/**
 * class for modeling hashtable with slots and linked entries in each slot
 * @param <K> type for keys in hashtable
 * @param <V> type for values in hashtable
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>> {
    private int size;
    private ArrayIndexedCollection<TableEntry<K, V>> slots;

    private int modificationCount;

    private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
        int slotIdx = 0;
        int left = size;
        TableEntry<K, V> current;
        boolean removed = true;
        int iteratorModificationCount;

        public IteratorImpl() {
            iteratorModificationCount = modificationCount;
        }

        @Override
        public boolean hasNext() {
            if (iteratorModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }

            return left != 0;
        }

        @Override
        public TableEntry<K, V> next() {
            if (iteratorModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }

            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            if (left == size) {                                                                                         // find first entry, jump over empty slots
                current = slots.get(slotIdx);
                while (current == null) {
                    current = slots.get(++slotIdx);
                }
            } else {                                                                                                    // if there's no next entry, jump over slots until you find one that's not empty
                if (current.next == null) {
                    do {
                        current = slots.get(++slotIdx);
                    } while (current == null);
                } else {
                    current = current.next;
                }

            }

            --left;
            removed = false;
            return current;
        }

        @Override
        public void remove() {
            if (iteratorModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }

            if (!removed) {
                SimpleHashtable.this.remove(current.getKey());
                removed = true;                                                                                         // each entry can only be removed once
                ++iteratorModificationCount;
            } else {
                throw new IllegalStateException("Cannot call remove method.");
            }
        }
    }

    @Override
    public Iterator<SimpleHashtable.TableEntry<K,V>> iterator() {

        return new IteratorImpl();
    }

    /**
     * class for modeling table entries in hashtable
     * @param <K> type of key in table entry
     * @param <V> type of value in table entry
     */
    public static class TableEntry<K, V> {
        private K key;
        private V value;
        private TableEntry<K, V> next;

        /**
         * @param key of new table entry
         * @param value of new table entry
         */
        public TableEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        /**
         * @return key of table entry
         */
        public K getKey() {
            return key;
        }

        /**
         * @return value of table entry
         */
        public V getValue() {
            return value;
        }

        /**
         * @param value to be set for given table entry
         */
        public void setValue(V value) {
            this.value = value;
        }
    }

    /**
     * default constructor
     */
    public SimpleHashtable() {
        this(16);
    }

    /**
     * @param initialCapacity the capacity will be set to the first power of 2 larger than or equal to initialCapacity
     * @throws IllegalArgumentException if initialCapacity is < 1
     */
    public SimpleHashtable(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException("Initial capacity cannot be < 1.");
        }

        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity *= 2;
        }

        this.size = 0;
        this.slots = new ArrayIndexedCollection<>(capacity);
        this.slots.setSize(capacity);
        this.modificationCount = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");

        for(TableEntry<K, V> element : this) {
            stringBuilder.append(element.key).append("=").append(element.value).append(", ");
        }

        stringBuilder.append("\b\b]");
        return stringBuilder.toString();
    }

    /**
     * @return capacity of collection
     */
    public int getCapacity() {
        return this.slots.size();
    }

    /**
     * @param key for new table entry to be added to hashtable
     * @param value for new table entry to be added to hashtable
     * @return old value if entry was overwritten
     * @throws NullPointerException if given key is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Key cannot be null.");
        }

        for(TableEntry<K, V> element : this) {                                                                          // if given key already exists in collection, replace the current value with the new value
            if (element.getKey().equals(key)) {
                V oldValue = element.getValue();
                element.setValue(value);
                return oldValue;
            }
        }

        if (this.size / (float) this.slots.size() >= 0.75) {                                                            // if capacity is filled up 75% or more, double table size
            doubleTableSize();
        }

        addNewEntry(key, value);                                                                                        // given key did not exist before so add new entry
        return null;
    }

    /**
     * @param key of the table entry we want to get
     * @return value of the table entry of the given key
     */
    public V get(Object key) {
        if (key == null) {
            return null;
        }

        for(TableEntry<K, V> element : this) {
            if (element.getKey().equals(key)) {
                return element.getValue();
            }
        }

        return null;
    }

    /**
     * @return number of entries in hashtable
     */
    public int size() {
        return this.size;
    }

    /**
     * @param key which we are searching for
     * @return true if hashtable contains given key
     */
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }

        for(TableEntry<K, V> element : this) {
            if (element.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param value which we are searching for
     * @return true if hashtable contains given value
     */
    public boolean containsValue(Object value) {
        for(TableEntry<K, V> element : this) {
            if (element.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param key of entry to be removed
     * @return value of the removed entry or null if the given key was not found in hashtable
     */
    public V remove(Object key) {
        if (key == null) {
            return null;
        }

        for (int i = 0; i < this.slots.size(); i++) {
            TableEntry<K, V> element = this.slots.get(i);
            TableEntry<K, V> prevElement;

            if (element != null) {
                if (element.getKey().equals(key)) {                                                                     // remove first entry in slot by replacing it with first next entry in the same slot
                    this.slots.set(i, element.next);
                    ++modificationCount;
                    --size;
                    return element.getValue();
                }

                prevElement = element;

                while (element.next != null) {                                                                          // remove entry from middle or end of slot by "jumping over" it with next pointers
                    if (element.getKey().equals(key)) {
                        prevElement.next = element.next;
                        ++modificationCount;
                        --size;
                        return element.getValue();
                    }

                    prevElement = element;
                    element = element.next;
                }

                if (element.getKey().equals(key)) {
                    prevElement.next = element.next;
                    ++modificationCount;
                    --size;
                    return element.getValue();
                }
            }
        }

        return null;
    }

    /**
     * @return true if hashtable is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return array representation of hashtable entries
     */
    @SuppressWarnings("unchecked")
    public TableEntry<K,V>[] toArray() {
        TableEntry<K, V>[] array = new TableEntry[this.size];
        int idx = 0;

        for(TableEntry<K, V> element : this) {
            array[idx++] = element;;
        }

        return array;
    }

    /**
     * clears hashtable but doesn't alter its capacity
     */
    public void clear() {
        int capacity = this.slots.size();

        this.slots.clear();
        this.slots.setSize(capacity);
        this.size = 0;
    }

    /**
     * doubles size of hashtable
     * it is called when the capacity of the collection is 75% or more filled up
     * to avoid too long linked lists of table entries in each slot
     */
    private void doubleTableSize() {
        int newCapacity = this.slots.size() * 2;
        ArrayIndexedCollection<TableEntry<K, V>> newSlots =
                new ArrayIndexedCollection<>(newCapacity);                                                              // create new slots and double the capacity

        TableEntry<K,V>[] array = this.toArray();
        this.clear();
        this.slots = newSlots;
        this.slots.setSize(newCapacity);

        for (TableEntry<K,V> entry : array) {                                                                           // fill new slots with old entries
            K key = entry.getKey();
            V value = entry.getValue();

            addNewEntry(key, value);
        }
    }

    /**
     * adds new entry to the beginning of calculated slot if it is empty or as next variable of the last entry in slot
     * @param key of new entry
     * @param value of new entry
     */
    private void addNewEntry(K key, V value) {
        ++modificationCount;
        int slot = abs(key.hashCode()) % this.slots.size();
        TableEntry<K, V> current = this.slots.get(slot);

        if (current == null) {

            this.slots.set(slot, new TableEntry<>(key, value));                                                         // add entry to beginning of slot

        } else {                                                                                                        // add entry ass next variable of the last entry in slot
            while (current.next != null) {
                current = current.next;
            }

            current.next = new TableEntry<>(key, value);
        }
        ++this.size;
    }
}
