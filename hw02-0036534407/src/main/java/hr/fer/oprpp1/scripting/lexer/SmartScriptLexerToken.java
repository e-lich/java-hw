package hr.fer.oprpp1.scripting.lexer;

import hr.fer.oprpp1.scripting.elems.Element;

public class SmartScriptLexerToken {
    private final SmartScriptLexerTokenType type;
    private final Element value;

    /**
     * @param type of new token; defined by TokenType enum
     * @param value of new token
     */
    public SmartScriptLexerToken(SmartScriptLexerTokenType type, Element value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return type of SmartScriptLexerToken
     */
    public SmartScriptLexerTokenType getType() {
        return type;
    }

    /**
     * @return value of SmartScriptLexerToken
     */
    public Element getValue() {
        return value;
    }
}
