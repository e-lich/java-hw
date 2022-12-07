package hr.fer.oprpp1.scripting.elems;

/**
 * class for storing operator in element
 */
public class ElementOperator extends Element {
    private String symbol;

    /**
     * @param symbol to be set as operator symbol of element
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }
}
