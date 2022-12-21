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
 * Command tree recursively lists the contents of the given directory.
 */
public class TreeShellCommand implements ShellCommand {

    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        ArrayList<String> argsArray = Util.getPathArgs(arguments);

        if (argsArray.size() != 1) {
            env.writeln("Invalid number of arguments!");
            return ShellStatus.CONTINUE;
        }

        File dir = Path.of(argsArray.get(0)).toFile();
        if (!dir.isDirectory()) {
            env.writeln("A directory must be provided!");
            return ShellStatus.CONTINUE;
        }

        printTree(dir, "");

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "tree";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>(List.of("""
                Command tree recursively lists the contents of the given directory.
                """.split("\n")));
        return Collections.unmodifiableList(commandDescription);
    }

    private static void printTree(File dir, String spaces) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(spaces + file.getName());
                if (file.isDirectory()) {
                    printTree(file, spaces + "  ");
                }
            }
        }
    }
}
