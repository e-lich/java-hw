package hr.fer.oprpp1.custom.collections;

/**
 * stack implementation using ArrayIndexedCollection
 */
public class ObjectStack<T> {
    private ArrayIndexedCollection<T> stack = new ArrayIndexedCollection<T>();

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
    public void push(T value) {
        this.stack.add(value);
    }

    /**
     * @return element at the top of the stack and remove it from stack
     */
    public T pop() {
        if (this.stack.size() == 0) {
            throw new EmptyStackException();
        }

        T value = this.stack.get(this.stack.size() - 1);
        this.stack.removeByIndex(this.stack.size() - 1);

        return value;
    }

    /**
     * @return element at the top of the stack and do not remove it from stack
     */
    public T peek() {
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
