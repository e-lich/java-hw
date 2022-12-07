package hr.fer.oprpp1.scripting.elems;

/**
 * class for storing function name in element
 */
public class ElementFunction extends Element {
    private String name;

    /**
     * @param name to be set as name of element
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    @Override
    public String asText() {
        return name;
    }
}
