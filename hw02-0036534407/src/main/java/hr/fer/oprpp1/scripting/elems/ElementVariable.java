package hr.fer.oprpp1.scripting.elems;

/**
 * class for storing variable name in element
 */
public class ElementVariable extends Element {
    private String name;

    /**
     * @param name to be set as name of element variable
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    @Override
    public String asText() {
        return name;
    }
}
