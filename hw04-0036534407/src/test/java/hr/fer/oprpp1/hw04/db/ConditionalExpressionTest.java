package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConditionalExpressionTest {

    @Test
    public void testConditionalExpression() {
        ConditionalExpression expression =
                new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Marta", ComparisonOperators.EQUALS);

        StudentRecord record = new StudentRecord("0000000003", "Šola", "Marta", 5);

        assertEquals(FieldValueGetters.FIRST_NAME, expression.getFieldGetter());
        assertEquals("Marta", expression.getLiteral());
        assertEquals(ComparisonOperators.EQUALS, expression.getComparisonOperator());

        assertTrue(expression.getComparisonOperator()
                .satisfied(expression
                        .getFieldGetter()
                        .get(record), expression.getLiteral()));
    }
}
