package hr.fer.oprpp1.hw04.db.parser;

import hr.fer.oprpp1.hw04.db.*;
import hr.fer.oprpp1.hw04.db.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.db.lexer.QueryLexerTokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that parses the query and creates a list of conditional expressions.
 */
public class QueryParser {
    List<ConditionalExpression> query;

    /**
     * Constructor that parses the query and creates a list of conditional expressions.
     * @param query the query to be parsed
     * @throws QueryParserException if the query is invalid
     */
    public QueryParser(String query) {
        QueryLexer lexer = new QueryLexer(query);
        this.query = new ArrayList<>();

        while (lexer.nextToken().getType() != QueryLexerTokenType.EOF) {                                                // while there are tokens
            IFieldValueGetter getter = null;
            String literal;
            IComparisonOperator operator = null;

            if (lexer.getToken().getType() != QueryLexerTokenType.ATTRIBUTE) {
                throw new QueryParserException("Expected attribute, got " + lexer.getToken().getType());                // if the first token is not an attribute, throw an exception
            } else {
                switch (lexer.getToken().getValue()) {                                                                  // if the first token is an attribute, set the getter
                    case "lastName":
                        getter = FieldValueGetters.LAST_NAME;
                        break;
                    case "firstName":
                        getter = FieldValueGetters.FIRST_NAME;
                        break;
                    case "jmbag":
                        getter = FieldValueGetters.JMBAG;
                        break;
                }
            }

            if (lexer.nextToken().getType() != QueryLexerTokenType.OPERATOR) {
                throw new QueryParserException("Expected operator, got " + lexer.getToken().getType());                 // if the second token is not an operator, throw an exception
            } else {
                switch (lexer.getToken().getValue()) {                                                                  // if the second token is an operator, set the operator
                    case "<":
                        operator = ComparisonOperators.LESS;
                        break;
                    case "<=":
                        operator = ComparisonOperators.LESS_OR_EQUALS;
                        break;
                    case ">":
                        operator = ComparisonOperators.GREATER;
                        break;
                    case ">=":
                        operator = ComparisonOperators.GREATER_OR_EQUALS;
                        break;
                    case "=":
                        operator = ComparisonOperators.EQUALS;
                        break;
                    case "!=":
                        operator = ComparisonOperators.NOT_EQUALS;
                        break;
                    case "LIKE":
                        operator = ComparisonOperators.LIKE;
                        break;
                }
            }

            if (lexer.nextToken().getType() != QueryLexerTokenType.STRING) {
                throw new QueryParserException("Expected string, got " + lexer.getToken().getType());                   // if the third token is not a string, throw an exception
            } else {
                literal = lexer.getToken().getValue();                                                                  // if the third token is a string, set the literal
            }

            if (lexer.nextToken().getType() != QueryLexerTokenType.LOGICAL_OPERATOR
                    && lexer.getToken().getType() != QueryLexerTokenType.EOF) {
                throw new QueryParserException("Expected logical operator, got " + lexer.getToken().getType());         // if the next token is not a logical operator, throw an exception
            }

            this.query.add(new ConditionalExpression(getter, literal, operator));                                       // create a new conditional expression and add it to the list
        }

    }

    /**
     * @return true if the query is direct, false otherwise
     * A query is direct if it contains only one conditional expression testing the jmbag attribute
     * and the operator is equals.
     */
    public boolean isDirectQuery() {
        if (query.size() == 1) {
            return query.get(0).getFieldGetter() == FieldValueGetters.JMBAG
                    && query.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
        }

        return false;
    }

    /**
     * @return the jmbag if the query is direct, null otherwise
     * @throws IllegalStateException if the query is not direct
     */
    public String getQueriedJMBAG() {
        if (this.isDirectQuery()) {
            return query.get(0).getLiteral();
        }

        throw new IllegalStateException("Query is not direct");
    }

    /**
     * @return the list of conditional expressions
     */
    public List<ConditionalExpression> getQuery() {
        return query;
    }
}
