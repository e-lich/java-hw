package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command help takes one or no arguments. If an argument is given, it prints help for that command. If no argument is given, it prints all available commands.
 */
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
        List<String> commandDescription = new ArrayList<>(List.of("""
                Command help takes one or no arguments.
                If an argument is given, it prints help for that command.
                If no argument is given, it prints all available commands.
                """.split("\n")));
        return Collections.unmodifiableList(commandDescription);
    }
}
