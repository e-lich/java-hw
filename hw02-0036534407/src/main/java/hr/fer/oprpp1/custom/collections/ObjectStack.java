package hr.fer.oprpp1.custom.collections;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * stack implementation using ArrayIndexedCollection
 */
public class ObjectStack {
    private ArrayIndexedCollection stack = new ArrayIndexedCollection();

    /**
     * @return true if there are no elements on stack
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * @return number of elements on stack
     */
    public int size() {
        return this.stack.size();
    }

    /**
     * @param value to be pushed to the top of the stack
     */
    public void push(Object value) {
        this.stack.add(value);
    }

    /**
     * @return element at the top of the stack and remove it from stack
     */
    public Object pop() {
        if (this.stack.size() == 0) {
            throw new EmptyStackException();
        }

        Object value = this.stack.get(this.stack.size() - 1);
        this.stack.removeByIndex(this.stack.size() - 1);

        return value;
    }

    /**
     * @return element at the top of the stack and do not remove it from stack
     */
    public Object peek() {
        if (this.stack.size() == 0) {
            throw new EmptyStackException();
        }

        return this.stack.get(this.stack.size() - 1);
    }

    /**
     * clear all elements from stack
     */
    public void clear() {
        this.stack.clear();
    }
}
