package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComparisonOperatorsTest {

    @Test
    public void testLike() {
        IComparisonOperator like = ComparisonOperators.LIKE;

        assertFalse(like.satisfied("Zagreb", "Aba*"));
        assertFalse(like.satisfied("AAA", "AA*AA"));
        assertFalse(like.satisfied("AAAAA", "AA*AAAAAA"));
        assertFalse(like.satisfied("BAAA", "AA*AA"));
        assertFalse(like.satisfied("AAAB", "AA*AA"));
        assertFalse(like.satisfied("Zagreb", "*Aba"));

        assertTrue(like.satisfied("Zagreb", "Za*"));
        assertTrue(like.satisfied("AAAA", "AA*AA"));
        assertTrue(like.satisfied("AAAA", "*AA"));
    }

    @Test
    public void testGreater() {
        IComparisonOperator greater = ComparisonOperators.GREATER;

        assertFalse(greater.satisfied("0000000001", "0000000001"));
        assertFalse(greater.satisfied("0000000001", "0000000002"));

        assertTrue(greater.satisfied("0000000002", "0000000001"));
    }

    @Test
    public void testGreaterOrEquals() {
        IComparisonOperator greaterOrEquals = ComparisonOperators.GREATER_OR_EQUALS;


        assertFalse(greaterOrEquals.satisfied("0000000001", "0000000002"));

        assertTrue(greaterOrEquals.satisfied("0000000001", "0000000001"));
        assertTrue(greaterOrEquals.satisfied("0000000002", "0000000001"));
    }

    @Test
    public void testLess() {
        IComparisonOperator less = ComparisonOperators.LESS;

        assertFalse(less.satisfied("0000000001", "0000000001"));
        assertFalse(less.satisfied("0000000002", "0000000001"));

        assertTrue(less.satisfied("0000000001", "0000000002"));
    }

    @Test
    public void testLessOrEquals() {
        IComparisonOperator lessOrEquals = ComparisonOperators.LESS_OR_EQUALS;

        assertFalse(lessOrEquals.satisfied("0000000002", "0000000001"));

        assertTrue(lessOrEquals.satisfied("0000000001", "0000000001"));
        assertTrue(lessOrEquals.satisfied("0000000001", "0000000002"));
    }

    @Test
    public void testEquals() {
        IComparisonOperator equals = ComparisonOperators.EQUALS;

        assertFalse(equals.satisfied("0000000001", "0000000002"));

        assertTrue(equals.satisfied("0000000001", "0000000001"));
    }

    @Test
    public void testNotEquals() {
        IComparisonOperator notEquals = ComparisonOperators.NOT_EQUALS;

        assertFalse(notEquals.satisfied("0000000001", "0000000001"));

        assertTrue(notEquals.satisfied("0000000001", "0000000002"));
    }

    @Test
    public void testTooManyWildcards() {
        IComparisonOperator like = ComparisonOperators.LIKE;
        assertThrows(IllegalArgumentException.class, () -> like.satisfied("AAAA", "A*A*A"));
    }
}
