package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Class that represents a token generated by {@link QueryLexer}.
 */
public class QueryLexerToken {

    private QueryLexerTokenType type;
    private String value;

    /**
     * @param type of token
     * @param value of token
     */
    public QueryLexerToken(QueryLexerTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return type of token
     */
    public QueryLexerTokenType getType() {
        return type;
    }

    /**
     * @return value of token
     */
    public String getValue() {
        return value;
    }
}