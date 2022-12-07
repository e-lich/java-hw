package hr.fer.oprpp1.hw02.prob1;

/**
 * the exception Lexers throw in case of error in tokenizing a text
 */
public class LexerException extends RuntimeException{

    public LexerException(String msg) {
        super(msg);
    }

}
