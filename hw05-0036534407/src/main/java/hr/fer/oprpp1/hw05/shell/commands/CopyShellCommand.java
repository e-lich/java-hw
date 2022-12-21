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

/**
 * Command copy copies file from source to destination. Takes 2 arguments: source file name and destination file name. If destination file exists, user is asked if he wants to overwrite it.
 */
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
            // Ask user if he wants to overwrite file
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
            // Create file if it doesn't exist
            dest = new File(dest.toURI());
        }

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(src));

            // If destination is directory, copy file to that directory with same name as source file
            if (dest.isDirectory()) {
                dest = new File(Path.of(dest.getPath() + "/" + src.getName()).toUri());
            }

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] inputLine;

            // Read file 4000 bytes at a time and write it to destination
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
        List<String> commandDescription = new ArrayList<>(List.of("""
                Command copy copies file from source to destination.
                Takes 2 arguments: source file name and destination file name.
                If destination file exists, user is asked if he wants to overwrite it.
                """.split("\n")));
        return Collections.unmodifiableList(commandDescription);
    }
}
