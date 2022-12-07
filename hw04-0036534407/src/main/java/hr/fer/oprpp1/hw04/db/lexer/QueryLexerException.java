package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Exception thrown by {@link QueryLexer} when an error occurs.
 */
public class QueryLexerException extends RuntimeException {

    /**
     * @param message of the exception
     */
    public QueryLexerException(String message) {
        super(message);
    }
}
