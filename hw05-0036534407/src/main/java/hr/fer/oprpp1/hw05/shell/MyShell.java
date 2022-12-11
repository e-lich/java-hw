package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.*;

import java.util.*;

public class MyShell implements Environment{
    private Character promptSymbol;
    private Character multiLineSymbol;
    private Character moreLinesSymbol;
    private Scanner sc;
    private SortedMap<String, ShellCommand> commands;
    public static void main(String[] args) {
        MyShell shell = new MyShell('>', '\\', '|');
        SortedMap<String, ShellCommand> commands = shell.commands();
        ShellStatus status;

        do {
            shell.write(shell.getPromptSymbol() + " ");
            StringBuilder commandLine = new StringBuilder();
            String line = shell.readLine();

            while (line != null && line.endsWith(shell.getMoreLinesSymbol().toString())) {
                commandLine.append(line, 0, line.length() - 1);
                shell.write(shell.getMultiLineSymbol() + " ");
                line = shell.readLine();
            }

            commandLine.append(line);

            String commandName = commandLine.toString().split(" ")[0];;
            String commandArgs = "";
            if (commandName.length() + 1 < commandLine.length()) {
                commandArgs = commandLine.substring(commandName.length() + 1);
            }
            ShellCommand command = commands.get(commandName);
            status = command.executeCommand(shell, commandArgs);
        } while (status != ShellStatus.TERMINATE);


    }

    public MyShell(Character promptSymbol, Character moreLinesSymbol, Character multiLineSymbol) {
        setPromptSymbol(promptSymbol);
        setMoreLinesSymbol(moreLinesSymbol);
        setMultiLineSymbol(multiLineSymbol);

        sc = new Scanner(System.in);

        commands = new TreeMap<>();
        commands.put("symbol", new SymbolShellCommand());
        commands.put("exit", new ExitShellCommand());
        commands.put("charsets", new CharsetsShellCommand());
        commands.put("cat", new CatShellCommand());
        commands.put("ls", new LsShellCommand());

        writeln("Welcome to MyShell v 1.0!");
    }

    @Override
    public String readLine() throws ShellIOException {
        try {
            return sc.nextLine();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
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