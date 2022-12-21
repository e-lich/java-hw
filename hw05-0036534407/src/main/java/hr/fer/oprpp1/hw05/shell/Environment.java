package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface Environment defines a contract for a shell environment.
 */
public interface Environment {
    /**
     * @return line read from user
     * @throws ShellIOException if an error occurs while reading
     */
    String readLine() throws ShellIOException;

    /**
     * @param text text to write to user
     * @throws ShellIOException if an error occurs while writing
     */
    void write(String text) throws ShellIOException;

    /**
     * @param text line to write to user
     * @throws ShellIOException if an error occurs while writing
     */
    void writeln(String text) throws ShellIOException;

    /**
     * @return sorted map of commands
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * @return multiline symbol
     */
    Character getMultiLineSymbol();

    /**
     * @param symbol new multiline symbol
     */
    void setMultiLineSymbol(Character symbol);

    /**
     * @return prompt symbol
     */
    Character getPromptSymbol();

    /**
     * @param symbol new prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * @return morelines symbol
     */
    Character getMoreLinesSymbol();

    /**
     * @param symbol new morelines symbol
     */
    void setMoreLinesSymbol(Character symbol);

}
