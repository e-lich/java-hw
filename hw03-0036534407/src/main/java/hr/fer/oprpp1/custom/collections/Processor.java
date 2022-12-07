package hr.fer.oprpp1.custom.collections;

public interface Processor<T> {
    /**
     * @param value to be processed by this method
     */
    void process(T value);
}
