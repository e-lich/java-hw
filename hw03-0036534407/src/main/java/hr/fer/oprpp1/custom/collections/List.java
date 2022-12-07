package hr.fer.oprpp1.custom.collections;

public interface List<T> extends Collection<T>{
    /**
     * @param index of the element we want to get from the collection
     * @return desired element
     * @throws IndexOutOfBoundsException in case of invalid index
     */
    T get(int index);

    /**
     * @param value which will be inserted to collection
     * @param position to which to value will be inserted
     */
    void insert(T value, int position);

    /**
     * @param value whose index we are requesting
     * @return index of value in collection
     */
    int indexOf(T value);

    /**
     * @param index of the element we want to remove from collection
     */
    void removeByIndex(int index);
}
