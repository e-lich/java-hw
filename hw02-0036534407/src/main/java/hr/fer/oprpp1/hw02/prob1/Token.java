package hr.fer.oprpp1.hw02.prob1;

/**
 * a single token of the text which we are tokenizing
 */
public class Token {
    private final TokenType type;
    private final Object value;

    /**
     * @param type of new token; defined by TokenType enum
     * @param value of new token
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return value of token
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return type of token; defined by TokenType enum
     */
    public TokenType getType() {
        return type;
    }
}
