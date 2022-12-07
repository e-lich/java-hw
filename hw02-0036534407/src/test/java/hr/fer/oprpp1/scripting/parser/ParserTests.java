package hr.fer.oprpp1.scripting.parser;

import hr.fer.oprpp1.scripting.elems.Element;
import hr.fer.oprpp1.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.scripting.nodes.EchoNode;
import hr.fer.oprpp1.scripting.nodes.ForLoopNode;
import hr.fer.oprpp1.scripting.nodes.TextNode;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class ParserTests {

    @Test
    public void forLoopTest() {
        SmartScriptParser parser = new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 10 1 $}
                  This is {$= i $}-th time this message is generated.
                {$END$}
                """);

        ForLoopNode forLoopNode = (ForLoopNode) parser.getDocumentNode().getChild(1);

        assertTrue(Objects.equals(forLoopNode.getVariable().asText(), "i")
                && Objects.equals(forLoopNode.getStartExpression().asText(), "1")
                && Objects.equals(forLoopNode.getEndExpression().asText(), "1")
                && Objects.equals(forLoopNode.getStepExpression().asText(), "10"));
    }

    @Test
    public void forLoopNoVariableTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR 1 1 10 1 $}
                  This is {$= i $}-th time this message is generated.
                {$END$}
                """));
    }

    @Test
    public void operatorInsideForLoopTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR i + 10 + $}
                  This is {$= i $}-th time this message is generated.
                {$END$}
                """));
    }

    @Test
    public void operatorAtEndOfForLoopTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 10 + $}
                  This is {$= i $}-th time this message is generated.
                {$END$}
                """));
    }

    @Test
    public void noStepExpressionInForLoopTest() {
        SmartScriptParser parser = new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 1 $}
                  This is {$= i $}-th time this message is generated.
                {$END$}
                """);

        ForLoopNode forLoopNode = (ForLoopNode) parser.getDocumentNode().getChild(1);

        assertNull(forLoopNode.getStepExpression());
    }

    @Test
    public void endTagNotEmptyTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 10 1 $}
                  This is {$= i $}-th time this message is generated.
                {$END bla$}
                """));
    }

    @Test
    public void invalidTagTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 10 1 $}
                  This is {$= i $}-th time this message is generated.
                {$ENDING$}
                """));
    }

    @Test
    public void notClosedForLoopTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 10 1 $}
                  This is {$= i $}-th time this message is generated.
                """));
    }

    @Test
    public void forLoopClosedTooManyTimesTest() {
        assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("""
                This is sample text.
                {$ FOR i 1 10 1 $}
                  This is {$= i $}-th time this message is generated.
                {$END$}
                {$END$}
                """));
    }

    @Test
    public void echoNodeTest() {
        SmartScriptParser parser = new SmartScriptParser("{$= i $}");

        EchoNode echoNode = (EchoNode) parser.getDocumentNode().getChild(0);
        Element[] elements = echoNode.getElements();

        assertEquals("i", elements[0].asText());
    }

    @Test
    public void textNodeTest() {
        SmartScriptParser parser = new SmartScriptParser("""
                This is sample text.
                """);

        TextNode textNode = (TextNode) parser.getDocumentNode().getChild(0);

        assertEquals(textNode.getText(), """
                This is sample text.
                """);
    }

    @Test
    public void parseDocumentToStringTest() {
        SmartScriptParser parser = new SmartScriptParser("""
                    This is sample text.
                    {$ FOR i 1 10 1 $}
                      This is {$= i $}-th time this message is generated.
                    {$END$}
                    {$FOR i 0 10 2 $}
                      sin({$=i$}^2) = {$= i i * @sin  "0.000" 1.234 @dec $}
                    {$END$}""");

        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();

        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();

        assertEquals(document, document2);
    }
}
