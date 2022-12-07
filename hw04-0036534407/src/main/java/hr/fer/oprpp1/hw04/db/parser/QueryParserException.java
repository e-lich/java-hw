package hr.fer.oprpp1.hw04.db.parser;

/**
 * Exception thrown when an error occurs while parsing a query.
 */
public class QueryParserException extends RuntimeException {

    /**
     * @param message of the exception
     */
    public QueryParserException(String message) {
        super(message);
    }

}
