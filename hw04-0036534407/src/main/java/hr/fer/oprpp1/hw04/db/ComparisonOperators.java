package hr.fer.oprpp1.hw04.db;

/**
 * Class that implements IComparisonOperator interface and compares two string values.
 */
public class ComparisonOperators {
    public static final IComparisonOperator LESS =
            ((value1, value2) -> value1.compareTo(value2) < 0);

    public static final IComparisonOperator LESS_OR_EQUALS =
            ((value1, value2) -> value1.compareTo(value2) < 0 || value1.compareTo(value2) == 0);

    public static final IComparisonOperator GREATER =
            ((value1, value2) -> value1.compareTo(value2) > 0);

    public static final IComparisonOperator GREATER_OR_EQUALS =
            ((value1, value2) -> value1.compareTo(value2) > 0 || value1.compareTo(value2) == 0);

    public static final IComparisonOperator EQUALS =
            ((value1, value2) -> value1.compareTo(value2) == 0);

    public static final IComparisonOperator NOT_EQUALS =
            ((value1, value2) -> value1.compareTo(value2) != 0);

    public static final IComparisonOperator LIKE =
            ((value1, value2) -> {
                if (value2.length() - 1 > value1.length()) {
                    return false;
                }

                // wildcard at beginning of pattern
                if (value2.charAt(0) == '*') {
                    for (int i = value2.length() - 1, j = value1.length() - 1; i > 0; --i, --j) {
                        if (checkValues(value1, value2, i, j)) {
                            return false;
                        }
                    }

                    return true;
                }

                // wildcard at end of pattern
                if (value2.charAt(value2.length() - 1) == '*') {
                    for (int i = 0; i < value2.length() - 1; ++i) {
                        if (checkValues(value1, value2, i, i)) {
                            return false;
                        }
                    }

                    return true;
                }

                // wildcard in the middle of pattern
                int idx = 0;
                while (value2.charAt(idx) != '*') {
                    if (checkValues(value1, value2, idx, idx)) {
                        return false;
                    }
                    ++idx;
                }
                for (int i = value2.length() - 1, j = value1.length() - 1; i > idx; --i, --j) {
                    if (checkValues(value1, value2, i, j)) {
                        return false;
                    }
                }

                return true;
                });

    /**
     * @param value1 first string value
     * @param value2 second string value
     * @param i index of value1
     * @param j index of value2
     * @return true if chars at given indexes are not equal, false otherwise
     * @throws IllegalArgumentException if the pattern contains more than one wildcard
     */
    private static boolean checkValues(String value1, String value2, int i, int j) {
        if(value2.charAt(i) == '*') {
            throw new IllegalArgumentException("Pattern cannot contain more than one wildcard.");
        }
        return value2.charAt(i) != value1.charAt(j);
    }
}
