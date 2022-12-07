package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class that filters records based on conditional expressions.
 */
public class QueryFilter implements IFilter {

    private List<ConditionalExpression> expressions;

    /**
     * @param expressions list of conditional expressions
     */
    public QueryFilter(List<ConditionalExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression expression : expressions) {
            if (!expression.getComparisonOperator().satisfied(expression.getFieldGetter().get(record),
                    expression.getLiteral())) {
                return false;
            }
        }
        return true;
    }
}
