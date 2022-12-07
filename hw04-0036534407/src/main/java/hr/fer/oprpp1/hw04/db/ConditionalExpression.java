package hr.fer.oprpp1.hw04.db;

/**
 * Class that represents a conditional expression.
 */
public class ConditionalExpression {

    private IFieldValueGetter fieldGetter;
    private String literal;
    private IComparisonOperator comparisonOperator;

    /**
     * @param fieldGetter field getter
     * @param literal string literal
     * @param comparisonOperator comparison operator
     */
    public ConditionalExpression (IFieldValueGetter fieldGetter, String literal, IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.literal = literal;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * @return comparison operator
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    /**
     * @return string literal
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * @return field getter
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }
}
