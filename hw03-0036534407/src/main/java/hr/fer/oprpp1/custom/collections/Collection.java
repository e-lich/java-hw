package hr.fer.oprpp1.custom.collections;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Collection<T> {

    /**
     * @return true if collection is empty
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @param other all elements from this collection will be added to the current collection
     */
    default void addAll(Collection<? extends T> other) {
        class LocalProcessor implements Processor<T> {
            @Override
            public void process(T value) {
                add(value);
            }
        }

        LocalProcessor lp = new LocalProcessor();
        other.forEach(lp);
    }

    /**
     * adds elements from col to current collection if the elements are accepted by the tester
     * @param col collection whose elements will be tested and added to current collection
     * @param tester tests all elements from col
     */
   default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
        ElementsGetter<? extends T> getter = col.createElementsGetter();

        while (getter.hasNextElement()) {
            T currentElement = getter.getNextElement();

            if (tester.test(currentElement)) {
                this.add(currentElement);
            }
        }
   }

    /**
     * @param processor which will be used to process each element of array
     */
    default void forEach(Processor<? super T> processor) {
        ElementsGetter<T> getter = createElementsGetter();

        while (getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * @param collection collection to which we will add transformed elements of the current collection
     * @param function function which will be used to transform elements
     * @param tester tests all transformed elements
     */
    default <R> void copyTransformedIntoIfAllowed(Collection<? super R> collection,
                                              Function<? super T, R> function,
                                              Predicate<? super R> tester) {
        ElementsGetter<T> getter = createElementsGetter();

        while (getter.hasNextElement()) {
            T currentElement = getter.getNextElement();
            R newElement = function.apply(currentElement);

            if (tester.test(newElement)) {
                collection.add(newElement);
            }
        }
    }

    /**
     * @return elements getter for the current collection
     */
    ElementsGetter<T> createElementsGetter();

    /**
     * @return size of collection
     */
    int size();

    /**
     * @param value is added to collection
     */
    void add(T value);

    /**
     * @param value which we are searching for in collection
     * @return true if value is in collection
     */
    boolean contains(Object value);

    /**
     * @param value to be removed from collection
     * @return true if value was successfully removed from collection
     */
    boolean remove(T value);

    /**
     * @return collection elements in parameterized type array
     */
    T[] toArray();

    /**
     * clear all elements from collection; empty collection
     */
    void clear();
}
