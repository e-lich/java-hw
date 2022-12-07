package hr.fer.oprpp1.scripting.nodes;

/**
 * node for storing document text
 */
public class TextNode extends Node {
    private String text;

    /**
     * @param text to be set as text of new TextNode
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * @return text of TextNode
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        if (text == null) {
            return "";
        }
        
        return text;
    }
}
