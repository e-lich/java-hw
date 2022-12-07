package hr.fer.oprpp1.scripting.elems;

/**
 * class for storing double numbers in element
 */
public class ElementConstantDouble extends Element {
    private double value;

    /**
     * @param value to be set as value of element
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return String.valueOf(value);
    }
}
