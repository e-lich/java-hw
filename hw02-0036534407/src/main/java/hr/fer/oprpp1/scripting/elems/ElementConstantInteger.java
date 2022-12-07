package hr.fer.oprpp1.scripting.elems;

/**
 * class for storing integer numbers in element
 */
public class ElementConstantInteger extends Element {
    private int value;

    /**
     * @param value to be set as value of element
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
