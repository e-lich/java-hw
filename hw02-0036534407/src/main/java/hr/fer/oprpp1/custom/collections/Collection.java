package hr.fer.oprpp1.custom.collections;

public interface Collection {

    /**
     * @return true if collection is empty
     */
    default boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * @param other all elements from this collection will be added to the current collection
     */
    default void addAll(Collection other) {
        class LocalProcessor implements Processor {
            @Override
            public void process(Object value) {
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
   default void addAllSatisfying(Collection col, Tester tester) {
        ElementsGetter getter = col.createElementsGetter();

        while (getter.hasNextElement()) {
            Object currentElement = getter.getNextElement();

            if (tester.test(currentElement)) {
                this.add(currentElement);
            }
        }
   }

    /**
     * @param processor which will be used to process each element of array
     */
    default void forEach(Processor processor) {
        ElementsGetter getter = createElementsGetter();

        while (getter.hasNextElement()) {
            processor.process(getter.getNextElement());
        }
    }

    /**
     * @return elements getter for the current collection
     */
    ElementsGetter createElementsGetter();

    /**
     * @return size of collection
     */
    int size();

    /**
     * @param value is added to collection
     */
    void add(Object value);

    /**
     * @param value which we are searching for in collection
     * @return true if value is in collection
     */
    boolean contains(Object value);

    /**
     * @param value to be removed from collection
     * @return true if value was successfully removed from collection
     */
    boolean remove(Object value);

    /**
     * @return collection elements in Object array
     */
    Object[] toArray();

    /**
     * clear all elements from collection; empty collection
     */
    void clear();
}
