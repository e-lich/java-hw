package hr.fer.oprpp1.scripting;

import static org.junit.jupiter.api.Assertions.*;

import hr.fer.oprpp1.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.scripting.nodes.EchoNode;
import hr.fer.oprpp1.scripting.nodes.TextNode;
import hr.fer.oprpp1.scripting.parser.SmartScriptParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ExtraTests {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void singleNodeTest(int number) {
        String text = readExample(number);
        DocumentNode documentNode = (new SmartScriptParser(text)).getDocumentNode();
        boolean isTextNode = true;

        try {
            TextNode textNode = (TextNode) documentNode.getChild(0);
        } catch (Exception ex) {
            isTextNode = false;
        }

        assertTrue(isTextNode && documentNode.numberOfChildren() == 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 7})
    public void twoNodesTest(int number) {
        String text = readExample(number);
        DocumentNode documentNode = (new SmartScriptParser(text)).getDocumentNode();
        boolean correctNodes = true;

        try {
            TextNode textNode = (TextNode) documentNode.getChild(0);
            EchoNode echoNode = (EchoNode) documentNode.getChild(1);
        } catch (Exception ex) {
            correctNodes = false;
        }

        assertTrue(correctNodes && documentNode.numberOfChildren() == 2);
    }

    private String readExample(int n) {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
            if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch(IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }
}
