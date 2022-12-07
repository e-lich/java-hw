package hr.fer.oprpp1.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * base class for all graph nodes
 */
public class Node {
    private ArrayIndexedCollection childNodes = null;

    /**
     * @param child which will be added to an internally managed collection of children
     */
    public void addChildNode(Node child) {
        if (childNodes == null) {
            childNodes = new ArrayIndexedCollection();
        }
        childNodes.add(child);
    }

    /**
     * @return number of (direct) children
     */
    public int numberOfChildren() {
        return childNodes.size();
    }

    /**
     * @param index of the child node to be returned
     * @return child node at selected index
     * @throws IndexOutOfBoundsException in case of invalid index
     */
    public Node getChild(int index) {
        return (Node) childNodes.get(index);
    }

}
