package hr.fer.oprpp1.custom.collections;

/**
 * collection which stores elements in Object field
 */
public class ArrayIndexedCollection extends Collection {
    private int size;
    private Object[] elements;

    public ArrayIndexedCollection() {
        this(16);
    }

    public ArrayIndexedCollection(int initialCapacity) {
        this(new Collection(), initialCapacity);
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
    }

    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        if (other == null) {
            throw new NullPointerException();
        }

        if (initialCapacity < other.size()) {
            this.elements = new Object[other.size()];
        } else {
            this.elements = new Object[initialCapacity];
        }

        this.addAll(other);
        this.size = other.size();
    }

    /**
     * @return number of elements in collection
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * @param value to be added to collection
     */
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }

        if (this.size == this.elements.length) {
            Object[] newElements = new Object[this.size * 2];                                                           // possibly convert object[] to collection and then do it with a constructor? or add a new constructor?
            System.arraycopy(this.elements, 0, newElements, 0, this.size);
            this.elements = newElements;
        }

        this.elements[this.size++] = value;
    }

    /**
     * @param value which we are checking for
     * @return returns true if collection contains value
     */
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

    /**
     * @return collection in form of Object array
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        System.arraycopy(this.elements, 0, array, 0, this.size);                                          // copy elements to Object array
        return array;
    }

    /**
     * @param processor which will process each element of collection
     */
    @Override
    public void forEach(Processor processor) {
        for (int i = 0; i < size; i++) {                                                                                // does foreach have to pass the null elements as well or only to size?
            processor.process(this.elements[i]);
        }
    }

    /**
     * removes all elements from collection
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            this.elements[i] = null;                                                                                    // backward array?
        }
        this.size = 0;
    }

    /**
     * @param value to be removed from collection
     * @return true if value was successfully removed from collection
     */
    @Override
    public boolean remove(Object value) {
        if (!this.contains(value)) {
            return false;
        }
        this.removeByIndex(this.indexOf(value));
        return true;
    }

    /**
     * @return collection.elements
     */
    public Object[] getElements() {
        return elements;
    }

    /**
     * @param index of the desired collection element
     * @return element of collection at desired index
     */
    public Object get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return this.elements[index];
    }

    /**
     * @param value to be inserted into collection
     * @param position to which the value is inserted
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > this.size) {
            throw new IndexOutOfBoundsException();
        }

        if (position == this.size) {                                                                                    // insert to end
            this.add(value);
        } else if (this.size == this.elements.length) {                                                                 // extend elements array and insert
            this.add(this.elements[this.size - 1]);
            System.arraycopy(this.elements, position, this.elements, position + 1, this.size - position - 1);
            this.elements[position] = value;
            ++this.size;
        } else {                                                                                                        // insert to position without extending elements array
            System.arraycopy(this.elements, position, this.elements, position + 1, this.size - position);
            this.elements[position] = value;
            ++this.size;
        }
    }

    /**
     * @param value which we want to know the index of
     * @return index of value in collection
     */
    public int indexOf(Object value) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param index of the element which we want to remove
     */
    public void removeByIndex(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(this.elements, index + 1, this.elements, index, this.size - index - 1);
        this.elements[--this.size] = null;
    }
}
