package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.db.lexer.QueryLexerException;
import hr.fer.oprpp1.hw04.db.lexer.QueryLexerTokenType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueryLexerTest {

    @Test
    public void testEOFToken() {
        QueryLexer lexer = new QueryLexer("jmbag");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testLessOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag<");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("<", lexer.getToken().getValue());
    }

    @Test
    public void testLessOrEqualOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag<=");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("<=", lexer.getToken().getValue());
    }

    @Test
    public void testGreaterOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag>");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals(">", lexer.getToken().getValue());
    }

    @Test
    public void testGreaterOrEqualOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag>=");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals(">=", lexer.getToken().getValue());
    }

    @Test
    public void testEqualOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag=");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("=", lexer.getToken().getValue());
    }

    @Test
    public void testNotEqualOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag!=");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("!=", lexer.getToken().getValue());
    }

    @Test
    public void testLikeOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbagLIKE");
        lexer.nextToken();
        assertEquals(QueryLexerTokenType.OPERATOR, lexer.nextToken().getType());
        assertEquals("LIKE", lexer.getToken().getValue());
    }

    @Test
    public void testFirstNameAttributeToken() {
        QueryLexer lexer = new QueryLexer("firstName");
        assertEquals(QueryLexerTokenType.ATTRIBUTE, lexer.nextToken().getType());
        assertEquals("firstName", lexer.getToken().getValue());
    }

    @Test
    public void testLastNameAttributeToken() {
        QueryLexer lexer = new QueryLexer("lastName");
        assertEquals(QueryLexerTokenType.ATTRIBUTE, lexer.nextToken().getType());
        assertEquals("lastName", lexer.getToken().getValue());
    }

    @Test
    public void testJmbagAttributeToken() {
        QueryLexer lexer = new QueryLexer("jmbag");
        assertEquals(QueryLexerTokenType.ATTRIBUTE, lexer.nextToken().getType());
        assertEquals("jmbag", lexer.getToken().getValue());
    }

    @Test
    public void testNotClosedString() {
        QueryLexer lexer = new QueryLexer("jmbag=\"0000000003");
        lexer.nextToken();
        lexer.nextToken();
        assertThrows(QueryLexerException.class, lexer::nextToken);
    }

    @Test
    public void testNoWhitespaceAroundAnd() {
        QueryLexer lexer = new QueryLexer("jmbag=\"0000000003\" andfirstName=\"Andrea\"");
        lexer.nextToken();
        lexer.nextToken();
        lexer.nextToken();
        assertThrows(QueryLexerException.class, lexer::nextToken);
    }

    @Test
    public void testAndLogicalOperatorToken() {
        QueryLexer lexer = new QueryLexer("jmbag=\"0000000003\" and firstName=\"Andrea\"");
        lexer.nextToken();
        lexer.nextToken();
        lexer.nextToken();

        assertEquals(QueryLexerTokenType.LOGICAL_OPERATOR, lexer.nextToken().getType());
        assertEquals("AND", lexer.getToken().getValue());
    }

    @Test
    public void testInvalidQuery() {
        QueryLexer lexer = new QueryLexer("banana");
        assertThrows(QueryLexerException.class, lexer::nextToken);
    }
}
