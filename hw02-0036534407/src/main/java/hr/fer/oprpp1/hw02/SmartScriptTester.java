package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.scripting.parser.SmartScriptParser;
import hr.fer.oprpp1.scripting.parser.SmartScriptParserException;

import java.io.IOException;

public class SmartScriptTester {
    public static void main(String[] args) throws IOException {
//        String docBody = Files.readString(Paths.get(args[0]));
        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser("""
                    This is sample text.
                    {$ FOR i 1 10 1 $}
                      This is {$= i $}-th time this message is generated.
                    {$END$}
                    {$FOR i 0 10 2 $}
                      sin({$=i$}^2) = {$= i i * @sin  "0.000" @dec $}
                    {$END$}""");
        } catch(SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch(Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        System.out.println(originalDocumentBody + "\n");
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
        System.out.println(document2 + "\n");
        boolean same = document.equals(document2);
        System.out.println("Succeeded:" + same);
    }
}
