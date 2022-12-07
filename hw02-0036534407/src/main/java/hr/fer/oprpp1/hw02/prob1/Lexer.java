package hr.fer.oprpp1.hw02.prob1;

/**
 * Lexers are used to tokenize given text
 */
public class Lexer {
    private final char[] data;
    private Token token;
    private int currentIndex;
    private LexerState state;

    /**
     * @param text which will be tokenized
     */
    public Lexer(String text) {
        this.data = text.toCharArray();
        this.currentIndex = 0;
        this.token = null;
        this.state = LexerState.BASIC;
    }

    /**
     * @return next token
     * @throws LexerException in case of error
     */
    public Token nextToken() {
        StringBuilder value = new StringBuilder();

        if (token != null && token.getType() == TokenType.EOF) {                                                        // if EOF token is returned, text is already tokenized
            throw new LexerException("Already tokenized entire text.");
        }

        if (state == LexerState.BASIC) {

            if (currentIndex == data.length) {                                                                          // end of text, return EOF token
                token = new Token(TokenType.EOF, null);
            } else if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {                          // tokenize words
                while (currentIndex < data.length &&
                        (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
                    if (data[currentIndex] == '\\') {                                                                   // check text for escaping
                        ++currentIndex;

                        if (currentIndex == data.length) {
                            throw new LexerException("No symbol or number after \\.");
                        }

                        if (Character.isLetter(data[currentIndex])) {
                            throw new LexerException("Redundant escape symbol before letter.");
                        }
                    }
                    value.append(data[currentIndex]);
                    ++currentIndex;
                }

                token = new Token(TokenType.WORD, value.toString());
            } else if (Character.isDigit(data[currentIndex])) {                                                         // tokenize numbers
                long numberValue;

                while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
                    value.append(data[currentIndex]);
                    ++currentIndex;
                }

                try {
                    numberValue = Long.parseLong(value.toString());
                } catch (Exception ex) {
                    throw new LexerException("Number type cannot be long.");
                }

                token = new Token(TokenType.NUMBER, numberValue);
            } else if (Character.isWhitespace(data[currentIndex])) {                                                    // skip whitespaces
                ++currentIndex;
                return nextToken();
            } else {
                if (data[currentIndex] == '#') {                                                                        // encountered tag symbol, switch to EXTENDED state
                    setState(LexerState.EXTENDED);
                }

                token = new Token(TokenType.SYMBOL, data[currentIndex]);
                ++currentIndex;
            }
        } else {

            if (currentIndex == data.length) {
                token = new Token(TokenType.EOF, null);                                                           // end of text, return EOF token
            } else if (Character.isWhitespace(data[currentIndex])) {                                                    // skip whitespaces
                ++currentIndex;
                return nextToken();
            } else {
                while (currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
                    if (data[currentIndex] == '#') {
                        setState(LexerState.BASIC);                                                                     // encountered tag symbol, switch back to BASIC state
                        token = new Token(TokenType.WORD, value.toString());
                        return token;
                    }

                    value.append(data[currentIndex]);
                    ++currentIndex;
                }

                token = new Token(TokenType.WORD, value.toString());
            }
        }

        return token;
    }

    /**
     * @return last generated token
     */
    public Token getToken() {
        return token;
    }


    /**
     * @param state new state for Lexer
     * @throws NullPointerException if null is passed as new state
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException();
        }
        this.state = state;
    }
}
