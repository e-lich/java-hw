package hr.fer.oprpp1.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.scripting.elems.Element;
import hr.fer.oprpp1.scripting.elems.ElementVariable;
import hr.fer.oprpp1.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.scripting.lexer.SmartScriptLexerToken;
import hr.fer.oprpp1.scripting.nodes.*;

import java.util.Objects;

import static hr.fer.oprpp1.scripting.lexer.SmartScriptLexerTokenType.*;

/**
 * class for parsing text documents
 */
public class SmartScriptParser {
    private DocumentNode document;

    /**
     * @param body to be parsed by new parser
     * @throws SmartScriptParserException in case of any exception
     */
    public SmartScriptParser(String body) {
        try {
            document = new DocumentNode();
            ObjectStack stack = new ObjectStack();

            stack.push(document);                                                                                       // push document node to stack

            SmartScriptLexer scriptLexer = new SmartScriptLexer(body);
            SmartScriptLexerToken token = scriptLexer.nextToken();

            do {
                if (token.getType() == TAG) {
                    SmartScriptLexer tagLexer = new SmartScriptLexer(token.getValue().asText());                        // encountered tag, initialize new lexer for analyzing tag body
                    tagLexer.setState(SmartScriptLexerState.TAG_BODY);
                    SmartScriptLexerToken tagToken = tagLexer.nextToken();

                    if (tagToken.getValue().asText().equalsIgnoreCase("=")) {                               // analyze echo tag
                        ArrayIndexedCollection elementsCollection = new ArrayIndexedCollection();

                        tagToken = tagLexer.nextToken();

                        while (tagToken.getType() != EOF) {
                            elementsCollection.add(tagToken.getValue());
                            tagToken = tagLexer.nextToken();
                        }

                        ((Node) stack.peek()).addChildNode(new EchoNode(toElementsArray(elementsCollection)));          // add echo node as childNode of the node at the top od the stack
                        token = scriptLexer.nextToken();
                    } else if (tagToken.getValue().asText().equalsIgnoreCase("for")) {                      // analyze for tag
                        ElementVariable variable;
                        Element[] elements = new Element[2];
                        ForLoopNode forLoopNode;

                        tagToken = tagLexer.nextToken();

                        if (tagToken.getType() != VARIABLE) {                                                           // check for variable at beginning of for loop
                            throw new SmartScriptParserException("Expected variable at beginning of for loop.");
                        } else {
                            variable = new ElementVariable(tagToken.getValue().asText());
                        }

                        for (int i = 0; i < 2; i++) {                                                                   // check for mandatory 2 expressions in for tag
                            tagToken = tagLexer.nextToken();

                            if (tagToken.getType() == VARIABLE
                                    || tagToken.getType() == STRING
                                    || tagToken.getType() == DOUBLE
                                    || tagToken.getType() == INTEGER) {

                                elements[i] = tagToken.getValue();
                            } else {
                                throw new SmartScriptParserException("Expected variable, string or number.");
                            }
                        }

                        tagToken = tagLexer.nextToken();

                        if (tagToken.getType() != EOF) {                                                                // check for optional 3rd expression in for tag
                            if (tagToken.getType() == VARIABLE
                                    || tagToken.getType() == STRING
                                    || tagToken.getType() == DOUBLE
                                    || tagToken.getType() == INTEGER) {

                                forLoopNode = new ForLoopNode(variable, elements[0], elements[1], tagToken.getValue());
                            } else {
                                throw new SmartScriptParserException("Expected variable, string or number.");
                            }
                        } else {
                            forLoopNode = new ForLoopNode(variable, elements[0], null, elements[1]);
                        }

                        ((Node) stack.peek()).addChildNode(forLoopNode);                                                // add new ForLoopNode as childNode of the node at the top of the stack
                        stack.push(forLoopNode);                                                                        // push ForLoopNode to stack since it's a non-empty tag
                        token = scriptLexer.nextToken();
                    } else if (tagToken.getValue().asText().equalsIgnoreCase("end")) {                      // analyze end tag
                        tagToken = tagLexer.nextToken();

                        if (tagToken.getType() != EOF) {
                            throw new SmartScriptParserException("End tag should be empty.");
                        }

                        stack.pop();                                                                                    // non-empty tag ended, remove it from the top of the stack
                        token = scriptLexer.nextToken();
                    } else {
                        throw new SmartScriptParserException("Invalid tag.");
                    }
                } else {                                                                                                // received document text node
                    if (!Objects.equals(token.getValue().asText(), "")) {
                        ((Node) stack.peek()).addChildNode(new TextNode(token.getValue().asText()));                    // add new TextNode as childNode of the node at the top of the stack
                    }
                    token = scriptLexer.nextToken();
                }
            } while (token.getType() != EOF);

            if (stack.size() != 1) {
                throw new SmartScriptParserException("Invalid number of loop openings and closings.");
            }
        } catch (Exception ex) {
            throw new SmartScriptParserException(ex.getMessage());
        }
    }

    /**
     * @param elementsCollection to be turned into Element[]
     * @return Element[] with elements of ElementCollection
     */
    private Element[] toElementsArray(ArrayIndexedCollection elementsCollection) {
        Element[] elements = new Element[elementsCollection.size()];
        int idx = 0;
        ElementsGetter getter = elementsCollection.createElementsGetter();

        while (getter.hasNextElement()) {
            elements[idx] = (Element) getter.getNextElement();
            ++idx;
        }

        return elements;
    }

    /**
     * @return document node of parser
     */
    public DocumentNode getDocumentNode() {
        return document;
    }
}
