package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Enum that represents all possible types of tokens that can be generated by {@link QueryLexer}.
 */
public enum QueryLexerTokenType {
    ATTRIBUTE, OPERATOR, STRING, LOGICAL_OPERATOR, EOF
}
