package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.parser.QueryParser;
import hr.fer.oprpp1.hw04.db.parser.QueryParserException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueryParserTest {

    @Test
    public void testNoAttribute() {
        assertThrows(QueryParserException.class, () -> new QueryParser("=\"0000000003\""));
    }

    @Test
    public void testFirstNameAttribute() {
        QueryParser parser = new QueryParser("firstName=\"Marta\"");

        assertEquals(parser.getQuery().get(0).getFieldGetter(), FieldValueGetters.FIRST_NAME);
    }

    @Test
    public void testLastNameAttribute() {
        QueryParser parser = new QueryParser("lastName=\"Kokolo\"");

        assertEquals(parser.getQuery().get(0).getFieldGetter(), FieldValueGetters.LAST_NAME);
    }

    @Test
    public void testJmbagAttribute() {
        QueryParser parser = new QueryParser("jmbag=\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getFieldGetter(), FieldValueGetters.JMBAG);
    }

    @Test
    public void testNoOperator() {
        assertThrows(QueryParserException.class, () -> new QueryParser("jmbag\"0000000003\""));
    }

    @Test
    public void testLessOperator() {
        QueryParser parser = new QueryParser("jmbag<\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.LESS);
    }

    @Test
    public void testLessOrEqualsOperator() {
        QueryParser parser = new QueryParser("jmbag<=\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.LESS_OR_EQUALS);
    }

    @Test
    public void testGreaterOperator() {
        QueryParser parser = new QueryParser("jmbag>\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.GREATER);
    }

    @Test
    public void testGreaterOrEqualsOperator() {
        QueryParser parser = new QueryParser("jmbag>=\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.GREATER_OR_EQUALS);
    }

    @Test
    public void testEqualsOperator() {
        QueryParser parser = new QueryParser("jmbag=\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.EQUALS);
    }

    @Test
    public void testNotEqualsOperator() {
        QueryParser parser = new QueryParser("jmbag!=\"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.NOT_EQUALS);
    }

    @Test
    public void testLikeOperator() {
        QueryParser parser = new QueryParser("jmbag LIKE \"0000000003\"");

        assertEquals(parser.getQuery().get(0).getComparisonOperator(), ComparisonOperators.LIKE);
    }

    @Test
    public void testNoLiteral() {
        assertThrows(QueryParserException.class, () -> new QueryParser("jmbag=lastName"));
    }

    @Test
    public void testNoLogicalOperator() {
        assertThrows(QueryParserException.class, () -> new QueryParser("jmbag=\"0000000003\" firstName"));
    }

    @Test
    public void testNotDirectQuery() {
        QueryParser parser = new QueryParser("jmbag=\"0000000003\" and firstName=\"Andrea\"");
        assertThrows(IllegalStateException.class, parser::getQueriedJMBAG);
    }

    @Test
    public void testDirectQuery() {
        QueryParser parser = new QueryParser("jmbag=\"0000000003\"");
        assertTrue(parser.isDirectQuery());
        assertEquals(parser.getQueriedJMBAG(), "0000000003");
    }
}
