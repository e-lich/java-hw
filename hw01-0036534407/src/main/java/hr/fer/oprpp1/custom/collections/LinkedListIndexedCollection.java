package hr.fer.oprpp1.custom.collections;


/**
 * linked list collection
 */
public class LinkedListIndexedCollection extends Collection {

    private static class ListNode {
        private ListNode previous = null;
        private ListNode next = null;
        private Object value;
    }

    private int size;
    private ListNode first;
    private ListNode last;

    public LinkedListIndexedCollection() {
        this(new Collection());
    }

    public LinkedListIndexedCollection(Collection other) {
        if (other == null) {
            throw new NullPointerException();
        }

        this.first = null;
        this.last = null;
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

        ListNode newNode = new ListNode();
        newNode.value = value;

        if (this.first == null) {                                                                                       // add to empty linked list
            this.first = newNode;
        } else {                                                                                                        // add to the end of non-empty linked list
            this.last.next = newNode;
            newNode.previous = this.last;
        }

        this.last = newNode;                                                                                            // declare newNode to be the last node of linked list
        ++this.size;
    }

    /**
     * @param value which we are checking for
     * @return returns true if collection contains value
     */
    @Override
    public boolean contains(Object value) {
        ListNode current = this.first;

        for (int i = 0; i < this.size; i++) {
            if (current.value == value) {
                return true;
            }

            current = current.next;
        }

        return false;
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
     * @return collection in form of Object array
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size];
        ListNode current = this.first;

        for (int i = 0; i < this.size; i++) {
            array[i] = current.value;

            current = current.next;
        }

        return array;
    }

    /**
     * @param processor which will process each element of collection
     */
    @Override
    public void forEach(Processor processor) {
        ListNode current = this.first;

        for (int i = 0; i < this.size; i++) {
            processor.process(current.value);

            current = current.next;
        }
    }

    /**
     * removes all elements from collection
     */
    @Override
    public void clear() {                                                                                               // forgets?
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    /**
     * @param index of the desired collection element
     * @return element of collection at desired index
     */
    public Object get(int index) {
        if (index < 0 || index > this.size - 1) {
            throw new IndexOutOfBoundsException();
        }

        ListNode current;

        if (index < this.size / 2 + 1) {                                                                                // get from first half of linked list
            current = this.first;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

        } else {                                                                                                        // get from second half of linked list
            current = this.last;

            for (int i = this.size - 1; i > index; i--) {
                current = current.previous;
            }

        }

        return current.value;
    }

    /**
     * @param value to be inserted into collection
     * @param position to which the value is inserted
     */
    public void insert(Object value, int position) {
        if (position < 0 || position > this.size) {
            throw new IndexOutOfBoundsException();
        }

        ListNode newNode = new ListNode();
        newNode.value = value;

        if (position == 0) {                                                                                            // insert node as first node of linked list
            newNode.next = this.first;
            this.first.previous = newNode;
            this.first = newNode;
        } else if (position == this.size) {                                                                             // insert node to end of linked list
            this.last.next = newNode;
            newNode.previous = this.last;
            this.last = newNode;
        } else {                                                                                                        // insert node somewhere to position, somewhere in the middle of linked list
            ListNode current = this.first;

            for (int i = 0; i < position; i++) {
                current = current.next;
            }

            newNode.next = current;
            newNode.previous = current.previous;
            current.previous.next = newNode;
            current.previous = newNode;
        }

        ++this.size;
    }

    /**
     * @param value which we want to know the index of
     * @return index of value in collection
     */
    public int indexOf(Object value) {
        ListNode current = this.first;

        for (int i = 0; i < this.size; i++) {
            if (current.value == value) {
                return i;
            }

            current = current.next;
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

        if (index == 0) {                                                                                               // remove first node in linked list
            this.first.next.previous = null;
            this.first = this.first.next;
        } else if (index == this.size - 1) {                                                                            // remove last node in linked list
            this.last.previous.next = null;
            this.last = this.last.previous;
        } else {                                                                                                        // remove node from somewhere in the middle of linked list
            ListNode current = this.first;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            current.previous.next = current.next;
            current.next.previous = current.previous;
        }

        --this.size;
    }
}
