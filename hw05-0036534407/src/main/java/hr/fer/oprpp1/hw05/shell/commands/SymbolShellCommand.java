package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.util.List;

public class SymbolShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] argsArray = Util.getSimpleCommandArgs(arguments);

        if (argsArray.length == 1) {
            switch (argsArray[0]) {
                case "PROMPT":
                    env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
                    break;
                case "MORELINES":
                    env.writeln("Symbol for MORELINES is '" + env.getMoreLinesSymbol() + "'");
                    break;
                case "MULTILINE":
                    env.writeln("Symbol for MULTILINE is '" + env.getMultiLineSymbol() + "'");
                    break;
                default:
                    env.writeln("Invalid symbol name: " + argsArray[0]);
            }
        } else if (argsArray.length == 2) {
            switch (argsArray[0]) {
                case "PROMPT":
                    env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() +
                            "' to '" + argsArray[1].charAt(0) + "'");
                    env.setPromptSymbol(argsArray[1].charAt(0));
                    break;
                case "MORELINES":
                    env.writeln("Symbol for MORELINES changed from '" + env.getMoreLinesSymbol() +
                            "' to '" + argsArray[1].charAt(0) + "'");
                    env.setMoreLinesSymbol(argsArray[1].charAt(0));
                    break;
                case "MULTILINE":
                    env.writeln("Symbol for MULTILINE changed from '" + env.getMultiLineSymbol() +
                            "' to '" + argsArray[1].charAt(0) + "'");
                    env.setMultiLineSymbol(argsArray[1].charAt(0));
                    break;
                default:
                    env.writeln("Invalid symbol name: " + argsArray[0]);
            }
        } else {
            env.writeln("Invalid number of arguments!");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "symbol";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
