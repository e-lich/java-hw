package hr.fer.oprpp1.scripting.lexer;

import hr.fer.oprpp1.scripting.elems.*;

/**
 * class for tokenizing text
 */
public class SmartScriptLexer {
    private final char[] data;
    private SmartScriptLexerToken token;
    private int currentIndex;
    private SmartScriptLexerState state;
    private boolean firstTagToken = true;

    /**
     * @param text to be tokenized by lexer
     */
    public SmartScriptLexer(String text) {
        this.data = text.toCharArray();
        this.currentIndex = 0;
        this.token = null;
        this.state = SmartScriptLexerState.TEXT;
    }

    /**
     * @return next token
     * @throws SmartScriptLexerException in case of lexer error
     */
    public SmartScriptLexerToken nextToken() {
        StringBuilder value = new StringBuilder();

        if (token != null && token.getType() == SmartScriptLexerTokenType.EOF) {
            throw new SmartScriptLexerException("Already tokenized entire document.");
        }

        if (currentIndex == data.length) {                                                                              // came to end of text, return EOF token
            token = new SmartScriptLexerToken(SmartScriptLexerTokenType.EOF, null);
            return token;
        }

        if (state == SmartScriptLexerState.TEXT) {                                                                      // analyze document text

            while (currentIndex < data.length
                    && (currentIndex + 1 == data.length
                        || data[currentIndex] != '{'
                        || data[currentIndex + 1] != '$')) {                                                            // goes through document text until it encounters start-of-tag symbols

                if (data[currentIndex] == '\\') {                                                                       // check for escaping in document text
                    ++currentIndex;

                    if (currentIndex == data.length) {
                        throw new SmartScriptLexerException("No symbol after \\.");
                    }

                    if (data[currentIndex] != '\\' && data[currentIndex] != '{') {
                        throw new SmartScriptLexerException("Invalid escaping for document text.");
                    }
                }

                value.append(data[currentIndex]);
                ++currentIndex;
            }

            token = new SmartScriptLexerToken(SmartScriptLexerTokenType.STRING, new ElementString(value.toString()));   // return document text as string token

            if ((currentIndex + 1 < data.length && data[currentIndex] == '{' && data[currentIndex + 1] == '$')) {       // if start-of-tag symbols were encountered, switch to analyzing the tag
                state = SmartScriptLexerState.TAG;
                currentIndex += 2;
            }
        } else if (state == SmartScriptLexerState.TAG_BODY) {                                                           // analyze tag body
            if (Character.isWhitespace(data[currentIndex])) {                                                           // skip whitespaces

                ++currentIndex;
                return nextToken();

            } else if (data[currentIndex] == '"') {                                                                     // analyze string token
                char whitespace = ' ';
                ++currentIndex;

                while (currentIndex < data.length && data[currentIndex] != '"') {
                    if (data[currentIndex] == '\\') {                                                                   // check for escaping in string token
                        ++currentIndex;

                        if (currentIndex + 1 == data.length && data[currentIndex] == '"') {
                            throw new SmartScriptLexerException("No symbol or number after \\.");
                        }

                        if (data[currentIndex] != '\\'
                                && data[currentIndex] != '\"'
                                && data[currentIndex] != 'n'
                                && data[currentIndex] != 'r'
                                && data[currentIndex] != 't') {
                            throw new SmartScriptLexerException("Invalid escaping for string.");
                        }

                        if (data[currentIndex] == 'n') {
                            whitespace = '\n';
                        } else if (data[currentIndex] == 'r') {
                            whitespace = '\r';
                        } else if (data[currentIndex] == 't') {
                            whitespace = '\t';
                        }
                    }

                    if (whitespace == ' ') {
                        value.append(data[currentIndex]);
                    } else {
                        value.append(whitespace);
                        whitespace = ' ';
                    }
                    ++ currentIndex;
                }

                if (currentIndex < data.length && data[currentIndex] == '"') {                                          // check if string was closed with ending "
                    ++currentIndex;
                    token = new SmartScriptLexerToken(SmartScriptLexerTokenType.STRING,
                            new ElementString(value.toString()));
                } else {
                    throw new SmartScriptLexerException("Invalid string format.");
                }
            } else if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '-') {                            // analyze numbers or - operator
                if (data[currentIndex] == '-'
                        && currentIndex + 1 < data.length
                        && !Character.isDigit(data[currentIndex + 1])) {                                                // if there's no number after - sign, return - sign as operator token

                    token = new SmartScriptLexerToken(SmartScriptLexerTokenType.OPERATOR,
                            new ElementOperator(String.valueOf(data[currentIndex])));
                    ++currentIndex;

                } else {
                    boolean doubleNumber = false;

                    value.append(data[currentIndex]);
                    ++currentIndex;

                    while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
                        value.append(data[currentIndex]);
                        ++currentIndex;
                    }

                    if (currentIndex + 1 < data.length
                            && data[currentIndex] == '.'
                            && Character.isDigit(data[currentIndex + 1])) {                                             // if dot is encountered and there are numbers after it,
                                                                                                                        // set doubleNumber to true and add the remaining digits to number
                        doubleNumber = true;

                        value.append(data[currentIndex]);
                        ++currentIndex;

                        while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
                            value.append(data[currentIndex]);
                            ++currentIndex;
                        }
                    }

                    if (doubleNumber) {
                        token = new SmartScriptLexerToken(SmartScriptLexerTokenType.DOUBLE,
                                new ElementConstantDouble(Double.parseDouble(value.toString())));                       // return double token if double number was encountered,
                    } else {
                        token = new SmartScriptLexerToken(SmartScriptLexerTokenType.INTEGER,
                                new ElementConstantInteger(Integer.parseInt(value.toString())));                        // otherwise, return integer token
                    }
                }

            } else if (data[currentIndex] == '+' || data[currentIndex] == '*'
                    || data[currentIndex] == '/' || data[currentIndex] == '^') {                                        // analyze operators

                token = new SmartScriptLexerToken(SmartScriptLexerTokenType.OPERATOR,
                        new ElementOperator(String.valueOf(data[currentIndex])));                                       // return operator token
                ++currentIndex;
            } else if (data[currentIndex] == '@') {                                                                     // analyze function
                value.append(data[currentIndex]);
                ++currentIndex;

                if (!Character.isLetter(data[currentIndex])) {
                    throw new SmartScriptLexerException("Invalid function name.");
                }

                while (currentIndex < data.length
                        && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '_')) {
                    value.append(data[currentIndex]);
                    ++currentIndex;
                }

                token = new SmartScriptLexerToken(SmartScriptLexerTokenType.FUNCTION,
                        new ElementFunction(value.toString()));                                                         // return function token

            } else if (firstTagToken) {                                                                                 // tokenize tag name
                firstTagToken = false;
                if (data[currentIndex] == '=') {
                    token = new SmartScriptLexerToken(SmartScriptLexerTokenType.VARIABLE,
                            new ElementVariable(String.valueOf(data[currentIndex])));
                    ++currentIndex;
                } else {
                    checkVariableName(value);
                }
            } else {                                                                                                    // analyze variable
                checkVariableName(value);
            }
        } else if (state == SmartScriptLexerState.TAG) {                                                                // analyze tag contents

            while (currentIndex < data.length) {
                if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '}') {     // encountered end of tag, switch state back to TEXT
                    currentIndex += 2;
                    state = SmartScriptLexerState.TEXT;
                    token = new SmartScriptLexerToken(SmartScriptLexerTokenType.TAG,
                            new ElementString(value.toString()));
                    return token;
                }

                value.append(data[currentIndex]);
                ++currentIndex;
            }

            throw new SmartScriptLexerException("Infinite tag.");

        }
        return token;
    }

    /**
     * @param value to build variable name string
     * @throws SmartScriptLexerException if variable name is invalid
     */
    private void checkVariableName(StringBuilder value) {
        if (!Character.isLetter(data[currentIndex])) {
            throw new SmartScriptLexerException("Invalid variable name.");
        }

        while (currentIndex < data.length
                && (Character.isLetter(data[currentIndex])
                || Character.isDigit(data[currentIndex])
                || data[currentIndex] == '_')) {

            value.append(data[currentIndex]);
            ++currentIndex;
        }

        token = new SmartScriptLexerToken(SmartScriptLexerTokenType.VARIABLE,
                new ElementVariable(value.toString()));
    }

    /**
     * @return last generated token
     */
    public SmartScriptLexerToken getToken() {
        return token;
    }

    /**
     * @param state new state for SmartScriptLexer
     */
    public void setState(SmartScriptLexerState state) {
        this.state = state;
    }
}
