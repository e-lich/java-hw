package hr.fer.oprpp1.custom.collections;

/**
 * @param <K> key type for pairs in dictionary
 * @param <V> value type for pairs in dictionary
 */
public class Dictionary<K, V> {
    /**
     * class for creating key-value pairs in dictionary
     */
    private class Pair {
        private K key;
        private V value;

        /**
         * @param key of new pair
         * @param value of new pair
         * @throws NullPointerException if key is null
         */
        public Pair(K key, V value) {
            if (key == null) {
                throw new NullPointerException("Key cannot be null.");
            }
            this.key = key;
            this.value = value;
        }

        /**
         * @return key of pair
         */
        public K getKey() {
            return key;
        }

        /**
         * @return value of pair
         */
        public V getValue() {
            return value;
        }
    }

    private ArrayIndexedCollection<Pair> dictionary;

    /**
     * default constructor for dictionary
     */
    public Dictionary() {
        this.dictionary = new ArrayIndexedCollection<>();
    }

    /**
     * @param key of new pair to be added to collection
     * @param value of new pair to be added to collection
     * @return old value of pair with same key if it existed in collection
     */
    public V put(K key, V value) {
        Pair element = findPair(key);

        if (element == null) {
            dictionary.add(new Pair(key, value));
            return null;
        } else {
            V oldValue = element.getValue();
            element.value = value;
            return oldValue;
        }
    }

    /**
     * @param key of value which we want to get
     * @return value for given key or null if given key doesn't exist in collection
     */
    public V get(Object key) {
        Pair element = findPair(key);

        if (element == null) {
            return null;
        }

        return element.getValue();
    }

    /**
     * @param key of pair to be removed
     * @return value of removed pair or null if given key was not found in collection
     */
    public V remove(K key) {
        Pair element = findPair(key);

        if (element == null) {
            return null;
        }

        this.dictionary.remove(element);
        return element.getValue();
    }

    /**
     * @param key of pair to be found
     * @return pair with given key or null if given key was not found in collection
     */
    private Pair findPair(Object key) {
        ElementsGetter<? extends Pair> getter = this.dictionary.createElementsGetter();

        while (getter.hasNextElement()) {
            Pair element = getter.getNextElement();

            if (element.getKey().equals(key)) {
                return element;
            }
        }

        return null;
    }
}
