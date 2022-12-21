package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command mkdir creates a directory. Takes one argument: path to the directory.
 */
public class MkdirShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        ArrayList<String> argsArray = Util.getPathArgs(arguments);

        if (argsArray.size() != 1) {
            env.writeln("Invalid number of arguments!");
            return ShellStatus.CONTINUE;
        }

        if (new File(Path.of(argsArray.get(0)).toUri()).mkdirs()) {
            env.writeln("Directories successfully created.");
        } else {
            env.writeln("Failed to create the directories.");
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "mkdir";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>(List.of("""
                Command mkdir creates a directory.
                Takes one argument: path to the directory.
                """.split("\n")));
        return Collections.unmodifiableList(commandDescription);
    }
}
