package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CatShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] argsArray = Util.getSimpleCommandArgs(arguments);

        if (argsArray.length != 1 && argsArray.length != 2) {
            env.writeln("Invalid number of arguments!");
            return ShellStatus.CONTINUE;
        }

        try (BufferedReader reader = argsArray.length == 2 ?
                Files.newBufferedReader(Path.of(argsArray[0]), Charset.forName(argsArray[1])) :
                Files.newBufferedReader(Path.of(argsArray[0]), Charset.defaultCharset())) {

            String fileLine = reader.readLine();
            while (fileLine != null) {
                env.writeln(fileLine);
                fileLine = reader.readLine();
            }
        } catch (Exception ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "cat";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
