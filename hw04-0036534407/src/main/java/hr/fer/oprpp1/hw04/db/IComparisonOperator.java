package hr.fer.oprpp1.hw04.db;

/**
 * Interface for comparing two strings.
 */
public interface IComparisonOperator {
    /**
     * @param value1 first string
     * @param value2 second string
     * @return true if the comparison condition is satisfied, false otherwise
     */
    boolean satisfied(String value1, String value2);
}
