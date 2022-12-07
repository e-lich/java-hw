package hr.fer.oprpp1.hw04.db.lexer;

/**
 * This class represents a lexer for a query. It is used to tokenize a query.
 */
public class QueryLexer {
    private final char[] data;
    private int currentIndex;
    private QueryLexerToken currentToken;

    /**
     * @param query query to be tokenized
     */
    public QueryLexer(String query) {
        this.data = query.toCharArray();
        this.currentIndex = 0;
    }

    /**
     * @return next token
     * @throws QueryLexerException if there is an error while tokenizing
     */
    public QueryLexerToken nextToken() {
        StringBuilder sb = new StringBuilder("");

        if (currentIndex >= data.length) {                                                                              // if we are at the end of the query return EOF token
            currentToken = new QueryLexerToken(QueryLexerTokenType.EOF, null);
            return currentToken;
        }

        if (Character.isWhitespace(data[currentIndex])) {                                                               // if we are at a whitespace, skip it
            currentIndex++;
            return nextToken();
        }

        if (data[currentIndex] == '<') {                                                                                // if we are at a '<' token, check if it is a '<=' token
            currentIndex++;

            if (currentIndex < data.length && data[currentIndex] == '=') {
                currentIndex++;

                currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, "<=");
                return currentToken;
            }

            currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, "<");
            return currentToken;
        } else if (data[currentIndex] == '>') {                                                                         // if we are at a '>' token, check if it is a '>=' token
            currentIndex++;

            if (currentIndex < data.length && data[currentIndex] == '=') {
                currentIndex++;

                currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, ">=");
                return currentToken;
            }

            currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, ">");
            return currentToken;
        } else if (data[currentIndex] == '=') {                                                                         // if we are at a '=' token, return it
            currentIndex++;

            currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, "=");
            return currentToken;
        } else if (data[currentIndex] == '!') {                                                                         // if we are at a '!' token, check if it is a '!=' token
            currentIndex++;

            if (currentIndex < data.length && data[currentIndex] == '=') {
                currentIndex++;

                currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, "!=");
                return currentToken;
            }
        } else if (data[currentIndex] == 'L' && currentIndex + 3 < data.length) {                                       // if we are at a 'LIKE' token, return it

            for (int i = 0; i < 4; i++) {
                sb.append(data[currentIndex + i]);
            }

            if (sb.toString().equals("LIKE")) {
                currentIndex += 4;

                currentToken = new QueryLexerToken(QueryLexerTokenType.OPERATOR, "LIKE");
                return currentToken;
            }
        } else if (data[currentIndex] == '"') {                                                                         // if we are at a string, return it
            currentIndex++;

            while (currentIndex < data.length && data[currentIndex] != '"') {
                sb.append(data[currentIndex]);
                currentIndex++;
            }

            if (currentIndex >= data.length) {
                throw new QueryLexerException("String literal not closed");                                             // if we are at the end of the query and the string is not closed, throw an exception
            }

            currentIndex++;

            currentToken = new QueryLexerToken(QueryLexerTokenType.STRING, sb.toString());
            return currentToken;
        } else if (String.valueOf(data[currentIndex]).equalsIgnoreCase("a")                                 // if we are at a logical operator, return it
                && currentIndex + 2 < data.length) {

            for (int i = 0; i < 3; i++) {
                sb.append(data[currentIndex + i]);
            }

            if (sb.toString().equalsIgnoreCase("and")) {
                if ((currentIndex - 1 >= 0 && !Character.isWhitespace(data[currentIndex - 1])) ||
                        (currentIndex + 3 < data.length && !Character.isWhitespace(data[currentIndex + 3]))) {
                    throw new QueryLexerException("Missing whitespaces around logical operator");                       // if there are no whitespaces around the logical operator, throw an exception
                }

                currentIndex += 4;

                currentToken = new QueryLexerToken(QueryLexerTokenType.LOGICAL_OPERATOR, "AND");
                return currentToken;
            }
        } else if (data[currentIndex] == 'f' && currentIndex + 8 < data.length) {                                       // if we are at a 'firstName' attribute token, return it

            for (int i = 0; i < 9; i++) {
                sb.append(data[currentIndex + i]);
            }

            if (sb.toString().equals("firstName")) {
                currentIndex += 9;

                currentToken = new QueryLexerToken(QueryLexerTokenType.ATTRIBUTE, "firstName");
                return currentToken;
            }
        } else if (data[currentIndex] == 'l' && currentIndex + 7 < data.length) {                                       // if we are at a 'lastName' attribute token, return it

            for (int i = 0; i < 8; i++) {
                sb.append(data[currentIndex + i]);
            }

            if (sb.toString().equals("lastName")) {
                currentIndex += 8;

                currentToken = new QueryLexerToken(QueryLexerTokenType.ATTRIBUTE, "lastName");
                return currentToken;
            }
        } else if (data[currentIndex] == 'j' && currentIndex + 4 < data.length) {                                       // if we are at a 'jmbag' attribute token, return it

            for (int i = 0; i < 5; i++) {
                sb.append(data[currentIndex + i]);
            }

            if (sb.toString().equals("jmbag")) {
                currentIndex += 5;

                currentToken = new QueryLexerToken(QueryLexerTokenType.ATTRIBUTE, "jmbag");
                return currentToken;
            }
        }

        throw new QueryLexerException("Invalid query");                                                                 // not token was recognized so the query is invalid, throw an exception
    }

    /**
     * @return the last generated token
     */
    public QueryLexerToken getToken() {
        return currentToken;
    }
}
