package hr.fer.oprpp1.scripting.nodes;

/**
 * parent node for all nodes in parsed text
 */
public class DocumentNode extends Node {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < this.numberOfChildren(); ++i) {
            stringBuilder.append(this.getChild(i));
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
