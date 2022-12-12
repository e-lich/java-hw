package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelpShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() == 0) {
            env.writeln("Available commands:");
            for (String commandName: env.commands().keySet()) {
                env.writeln(commandName);
            }
        } else {
            String[] argsArray = Util.getSimpleCommandArgs(arguments);
            if (argsArray.length != 1) {
                env.writeln("Invalid number of arguments!");
                return ShellStatus.CONTINUE;
            }
            ShellCommand command = env.commands().get(arguments);
            env.writeln("Information for command: " + command.getCommandName());
            List<String> info = command.getCommandDescription();

            for (String line: info) {
                env.writeln(line);
            }
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add("Takes 1 argument or no arguments.");
        commandDescription.add("If no argument is given, prints all available commands to console.");
        commandDescription.add("If one argument is given, prints command description for the given command to console.");
        return Collections.unmodifiableList(commandDescription);
    }
}
