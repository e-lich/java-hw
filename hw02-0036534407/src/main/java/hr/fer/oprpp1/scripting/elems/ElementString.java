package hr.fer.oprpp1.scripting.elems;

/**
 * class for storing string in element
 */
public class ElementString extends Element {
    private String value;

    /**
     * @param value to be set as value of element
     */
    public ElementString(String value) {
        this.value = value;
    }

    @Override
    public String asText() {
        return value;
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        }

        return "\"" + value + "\"";
    }
}
