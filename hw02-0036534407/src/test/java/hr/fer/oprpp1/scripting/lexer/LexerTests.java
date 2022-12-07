package hr.fer.oprpp1.scripting.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LexerTests {

    @Test
    public void alreadyTokenizedEntireTextTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("This");

        scriptLexer.nextToken();
        scriptLexer.nextToken();

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void noSymbolAfterEscapeSignInDocumentTextTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("this\\");

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void invalidEscapingInDocumentTextTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("this\\a");

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void noSymbolAfterEscapeSignInTagStringTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("\"\\\"");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void invalidEscapingInTagStringTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("\"\\a\"");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void whitespacesInStringTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("\"\\n \\t \\r\"");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertDoesNotThrow(scriptLexer::nextToken);
    }

    @Test
    public void invalidStringFormatTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("\"bla bla");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void minusBeforeCharacterTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("-a");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        scriptLexer.nextToken();

        assertEquals(scriptLexer.getToken().getType(), SmartScriptLexerTokenType.OPERATOR);
    }

    @Test
    public void doubleNumberTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("-1.3456");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertEquals(scriptLexer.nextToken().getType(), SmartScriptLexerTokenType.DOUBLE);
    }

    @Test
    public void invalidFunctionNameTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("@1");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void infiniteTagTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("{$ wow ");
        scriptLexer.nextToken();

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }

    @Test
    public void invalidVariableNameTest() {
        SmartScriptLexer scriptLexer = new SmartScriptLexer("_wow");
        scriptLexer.setState(SmartScriptLexerState.TAG_BODY);

        assertThrows(SmartScriptLexerException.class, scriptLexer::nextToken);
    }
}
