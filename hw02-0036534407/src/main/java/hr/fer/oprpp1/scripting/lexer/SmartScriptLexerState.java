package hr.fer.oprpp1.scripting.lexer;

/**
 * state of SmartScriptLexer
 * TEXT - analyzing document text
 * TAG - analyzing contents of a tag
 * TAG_BODY - analyzing tag body
 */
public enum SmartScriptLexerState {
    TEXT, TAG, TAG_BODY
}
