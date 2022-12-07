package hr.fer.oprpp1.custom.collections;

public interface Tester<T> {
    /**
     * @param obj to be tested
     * @return true if tester accepts given object
     */
    boolean test(T obj);
}
