package hr.fer.oprpp1.scripting.nodes;

import hr.fer.oprpp1.scripting.elems.Element;
import hr.fer.oprpp1.scripting.elems.ElementVariable;

/**
 * node for storing variable and expressions of for tag
 */
public class ForLoopNode extends Node {
    private ElementVariable variable;
    private Element startExpression;
    private Element endExpression;
    private Element stepExpression;

    /**
     * @param variable to be set as variable of new ForLoopNode
     * @param startExpression to be set as startExpression of new ForLoopNode
     * @param stepExpression to be set as stepExpression of new ForLoopNode
     * @param endExpression to be set as endExpression of new ForLoopNode
     */
    public ForLoopNode(ElementVariable variable, Element startExpression,
                       Element stepExpression, Element endExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * @return variable of ForLoopNode
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * @return startExpression of ForLoopNode
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * @return endExpression of ForLoopNode
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * @return stepExpression of ForLoopNode
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{$FOR").append(" ")
                .append(variable.asText()).append(" ")
                .append(startExpression.asText()).append(" ");
        if (stepExpression != null) {
            stringBuilder.append(stepExpression.asText()).append(" ");
        }
        stringBuilder.append(endExpression.asText()).append("$}");

        for (int i = 0; i < this.numberOfChildren(); ++i) {
            stringBuilder.append(this.getChild(i));
        }

        stringBuilder.append("{$END$}");

        return stringBuilder.toString();
    }
}
