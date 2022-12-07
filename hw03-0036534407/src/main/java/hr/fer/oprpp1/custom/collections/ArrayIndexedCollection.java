package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * collection which stores elements in Object field
 */
public class ArrayIndexedCollection<T> implements List<T> {
    private int size;
    private T[] elements;
    private long modificationCount = 0;

    /**
     * default constructor
     */
    public ArrayIndexedCollection() {
        this(16);
    }

    /**
     * @param initialCapacity to be set as initial capacity of collection
     * @throws IllegalArgumentException if initial capacity is less than 1
     */
    @SuppressWarnings("unchecked")
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }

        this.elements = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * @param other collection to be added to new collection
     * @param initialCapacity to be set ad initial capacity of collection
     * @throws NullPointerException if null is passed for other collection
     */
    @SuppressWarnings("unchecked")
    public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (initialCapacity < other.size()) {
            this.elements = (T[]) new Object[other.size()];
        } else {
            this.elements = (T[]) new Object[initialCapacity];
        }

        this.addAll(other);
        this.size = other.size();
    }

    /**
     * class for getting elements of collection
     */
    private static class ArrayCollectionElementsGetter<E> implements ElementsGetter<E> {
        private int idx = 0;
        private final ArrayIndexedCollection<E> col;
        private final long savedModificationCount;

        ArrayCollectionElementsGetter(ArrayIndexedCollection<E> col) {
            this.col = col;
            this.savedModificationCount = col.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if (this.savedModificationCount != col.modificationCount) {
                throw new ConcurrentModificationException();
            }
            return idx < col.size;
        }

        @Override
        public E getNextElement() {
            if (this.savedModificationCount != col.modificationCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNextElement()) {
                throw new NoSuchElementException();
            }
            return col.get(idx++);
        }
    }

    @Override
    public ElementsGetter<T> createElementsGetter() {
        return new ArrayCollectionElementsGetter<>(this);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(T value) {
        if (value == null) {
            throw new NullPointerException();
        }

        if (this.size == this.elements.length) {
            Object[] newElements = new Object[this.size * 2];                                                           // possibly convert object[] to collection and then do it with a constructor? or add a new constructor?
            System.arraycopy(this.elements, 0, newElements, 0, this.size);
            this.elements = (T[]) newElements;
        }

        this.elements[this.size++] = value;
        ++this.modificationCount;
    }

    @Override
    public boolean contains(Object value) {
        if (this.size == 0) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[this.size];
        System.arraycopy(this.elements, 0, array, 0, this.size);                                         // copy elements to Object array
        return array;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.elements[i] = null;
        }
        this.size = 0;
        ++this.modificationCount;
    }

    @Override
    public boolean remove(T value) {
        if (!this.contains(value)) {
            return false;
        }
        this.removeByIndex(this.indexOf(value));
        return true;
    }

    public T get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return this.elements[index];
    }

    public void insert(T value, int position) {
        if (position < 0 || position > this.size) {
            throw new IndexOutOfBoundsException();
        }

        if (position == this.size) {                                                                                    // insert to end
            this.add(value);
        } else if (this.size == this.elements.length) {                                                                 // extend elements array and insert
            this.add(this.elements[this.size - 1]);
            System.arraycopy(this.elements, position, this.elements,
                    position + 1, this.size - position - 1);
            this.elements[position] = value;
            ++this.size;
        } else {                                                                                                        // insert to position without extending elements array
            System.arraycopy(this.elements, position, this.elements, position + 1, this.size - position);
            this.elements[position] = value;
            ++this.size;
        }

        ++this.modificationCount;
    }

    public int indexOf(Object value) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void removeByIndex(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(this.elements, index + 1, this.elements, index, this.size - index - 1);
        this.elements[--this.size] = null;
        ++this.modificationCount;
    }

    /**
     * @param index in the collection for the given value
     * @param value to be set at given index in collection
     */
    public void set(int index, T value) {
        elements[index] = value;
    }

    /**
     * @param size to be set as size of collection
     * use only when using this collection for SimpleHashtable
     */
    public void setSize(int size) {
        this.size = size;
    }
}
