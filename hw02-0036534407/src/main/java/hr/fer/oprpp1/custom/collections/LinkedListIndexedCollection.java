package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * linked list collection
 */
public class LinkedListIndexedCollection implements List {

    private static class ListNode {
        private ListNode previous = null;
        private ListNode next = null;
        private Object value;
    }

    private long modificationCount = 0;
    private int size = 0;
    private ListNode first = null;
    private ListNode last = null;

    /**
     * default constructor
     */
    public LinkedListIndexedCollection() {}

    /**
     * @param other to be added to new collection
     * @throws NullPointerException in case null is passed for other collection
     */
    public LinkedListIndexedCollection(Collection other) {
        if (other == null) {
            throw new NullPointerException();
        }

        this.addAll(other);
        this.size = other.size();
    }

    /**
     * class for getting elements of collection
     */
    private static class LinkedListCollectionElementsGetter implements ElementsGetter {
        private ListNode current;
        private final LinkedListIndexedCollection col;
        private final long savedModificationCount;
        private boolean hasNext = true;

        LinkedListCollectionElementsGetter(LinkedListIndexedCollection col) {
            this.current = col.first;
            this.col = col;
            this.savedModificationCount = col.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if (this.savedModificationCount != col.modificationCount) {
                throw new ConcurrentModificationException();
            }
            return hasNext;
        }

        @Override
        public Object getNextElement() {
            if (this.savedModificationCount != col.modificationCount) {
                throw new ConcurrentModificationException();
            }
            if (!hasNextElement()) {
                throw new NoSuchElementException();
            }

            Object value = current.value;

            if (current.equals(col.last)) {
                this.hasNext = false;
            } else {
                current = current.next;
            }

            return value;
        }
    }

    @Override
    public ElementsGetter createElementsGetter() {
        return new LinkedListCollectionElementsGetter(this);
    }

    @Override
    public int size() {
        return this.size;
    }

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
        ++this.modificationCount;
    }

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

    @Override
    public boolean remove(Object value) {
        if (!this.contains(value)) {
            return false;
        }
        this.removeByIndex(this.indexOf(value));
        return true;
    }

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

    @Override
    public void clear() {                                                                                               // forgets?
        this.first = null;
        this.last = null;
        this.size = 0;
        ++this.modificationCount;
    }

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
        ++this.modificationCount;
    }

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
        ++this.modificationCount;
    }
}
