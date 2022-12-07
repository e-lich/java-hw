package hr.fer.oprpp1.custom.collections;

public interface ElementsGetter {
    /**
     * @return true if collection has more "unread" elements
     */
    boolean hasNextElement();

    /**
     * @return next "unread" element of collection
     */
    Object getNextElement();

    /**
     * @param p processor used to process all "unread" elements of collection
     */
    default void processRemaining(Processor p) {
        while (hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
