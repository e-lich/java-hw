package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CopyShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        ArrayList<String> argsArray = Util.getPathArgs(arguments);

        if (argsArray.size() != 2) {
            env.writeln("Invalid number of arguments!");
            return ShellStatus.CONTINUE;
        }

        File src = Path.of(argsArray.get(0)).toFile();
        File dest = Path.of(argsArray.get(1)).toFile();

        if (dest.exists() && !dest.isDirectory()) {
            env.writeln("Are you sure the file " + argsArray.get(1) + " should be overwritten? (Y/n)");
            String line = env.readLine();

            while (!line.equalsIgnoreCase("n")
                    && !line.equalsIgnoreCase("y")
                    && !line.equals("")) {
                env.writeln("Invalid answer.");
                env.writeln("Are you sure the file " + argsArray.get(1) + " should be overwritten? (Y/n)");
                line = env.readLine();
            }

            if (line.equals("n")) {
                env.writeln("Quitting copying the file.");
                return ShellStatus.CONTINUE;
            }
        } else {
            dest = new File(dest.toURI());
        }

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(src));

            if (dest.isDirectory()) {
                dest = new File(Path.of(dest.getPath() + "/" + src.getName()).toUri());
            }

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] inputLine;

            while ((inputLine = inputStream.readNBytes(4000)).length != 0) {
                outputStream.write(inputLine);
            }

            inputStream.close();
            outputStream.close();

            env.writeln("File copied.");
        } catch (Exception ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "copy";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add("Takes 2 arguments, source and destination file.");
        commandDescription.add("If the specified destination file exists, checks if it should be overwritten.");
        commandDescription.add("Copies source file to destination file.");
        return Collections.unmodifiableList(commandDescription);
    }
}
