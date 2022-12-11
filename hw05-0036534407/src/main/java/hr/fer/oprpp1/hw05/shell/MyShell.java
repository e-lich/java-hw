package hr.fer.oprpp1.hw05.shell;

import java.util.*;

public class MyShell implements Environment{
    private Character promptSymbol;
    private Character multiLineSymbol;
    private Character moreLinesSymbol;
    private Scanner sc;
    private SortedMap<String, ShellCommand> commands;
    public static void main(String[] args) {
        MyShell shell = new MyShell('>', '\\', '|');



    }

    public MyShell(Character promptSymbol, Character moreLinesSymbol, Character multiLineSymbol) {
        setPromptSymbol(promptSymbol);
        setMoreLinesSymbol(moreLinesSymbol);
        setMultiLineSymbol(multiLineSymbol);

        sc = new Scanner(System.in);

        commands = new TreeMap<>();

        System.out.println("Welcome to MyShell v 1.0!");
    }

    @Override
    public String readLine() throws ShellIOException {
        try {
            return sc.nextLine();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    @Override
    public void write(String text) throws ShellIOException {
        try {
            System.out.print(text);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void writeln(String text) throws ShellIOException {
        try {
            System.out.println(text);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
    }

    @Override
    public Character getMultiLineSymbol() {
        return multiLineSymbol;
    }

    @Override
    public void setMultiLineSymbol(Character symbol) {
        multiLineSymbol = symbol;
    }

    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    @Override
    public void setPromptSymbol(Character symbol) {
        promptSymbol = symbol;
    }

    @Override
    public Character getMoreLinesSymbol() {
        return moreLinesSymbol;
    }

    @Override
    public void setMoreLinesSymbol(Character symbol) {
        moreLinesSymbol = symbol;
    }
}
