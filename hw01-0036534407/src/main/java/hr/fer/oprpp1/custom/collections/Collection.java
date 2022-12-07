package hr.fer.oprpp1.custom.collections;

public class Collection {
    protected Collection() {}

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return 0;
    }

    public void add(Object value) {}

    public boolean contains(Object value) {
        return false;
    }

    public boolean remove(Object value) {
        return false;
    }

    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    public void forEach(Processor processor) {}

    public void addAll(Collection other) {
        class LocalProcessor extends Processor {
            @Override
            public void process(Object value) {
                add(value);
            }
        }

        LocalProcessor lp = new LocalProcessor();
        other.forEach(lp);
    }

    public void clear() {}
}
