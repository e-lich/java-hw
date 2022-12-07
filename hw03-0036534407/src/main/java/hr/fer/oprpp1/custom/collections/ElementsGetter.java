package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter<T> {
    /**
     * @return true if collection has more "unread" elements
     */
    boolean hasNextElement();

    /**
     * @return next "unread" element of collection
     */
    T getNextElement();

    /**
     * @param p processor used to process all "unread" elements of collection
     */
    default void processRemaining(Processor<? super T> p) {
        while (hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
