package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

public interface Environment {
    String readLine() throws ShellIOException;
    void write(String text) throws ShellIOException;
    void writeln(String text) throws ShellIOException;
    SortedMap<String, ShellCommand> commands();
    Character getMultiLineSymbol();
    void setMultiLineSymbol(Character symbol);
    Character getPromptSymbol();
    void setPromptSymbol(Character symbol);
    Character getMoreLinesSymbol();
    void setMoreLinesSymbol(Character symbol);

}
