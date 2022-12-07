package hr.fer.oprpp1.scripting.nodes;

import hr.fer.oprpp1.scripting.elems.Element;
import hr.fer.oprpp1.scripting.elems.ElementString;

/**
 * node for storing contents of echo tag
 */
public class EchoNode extends Node {
    private Element[] elements;

    /**
     * @param elements to be set as elements of new echo node
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * @return elements of echo node
     */
    public Element[] getElements() {
        return elements;
    }

    @Override
    public String toString() {
        if (elements == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("{$=");

        for (Element element : elements) {
            try {
                ElementString elementString = (ElementString) element;
                stringBuilder.append(elementString.toString()).append(" ");
            } catch (Exception ex) {
                stringBuilder.append(element.asText()).append(" ");
            }
        }

        stringBuilder.append("$}");

        return stringBuilder.toString();
    }
}
