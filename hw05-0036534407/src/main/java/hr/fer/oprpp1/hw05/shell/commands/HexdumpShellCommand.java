package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command hexdump prints the given file in hexadecimal format.
 */
public class HexdumpShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        ArrayList<String> argsArray = Util.getPathArgs(arguments);

        if (argsArray.size() != 1) {
            env.writeln("Invalid number of arguments!");
            return ShellStatus.CONTINUE;
        }

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(Path.of(argsArray.get(0)).toFile()));

            byte[] inputLine;
            int processedBytes = 0;
            String hexProcessedBytes;

            while ((inputLine = inputStream.readNBytes(16)).length != 0) {
                StringBuilder sb = new StringBuilder();
                processedBytes += 16;
                hexProcessedBytes = Integer.toHexString(processedBytes);
                env.write("0".repeat(8 - hexProcessedBytes.length()) + hexProcessedBytes + ": ");

                for (int i = 0; i < 8; i++) {
                    if (i < inputLine.length) {
                        if (inputLine[i] >= 32 && inputLine[i] <= 127) {
                            sb.append((char) inputLine[i]);
                        } else {
                            sb.append(".");
                        }
                        String hexString = Integer.toHexString(inputLine[i]);
                        env.write("0".repeat(2 - hexString.length()) + hexString.toUpperCase() + " ");
                    } else  {
                        env.write("   ");
                    }
                }

                env.write("\b|");

                for (int i = 0; i < 8; i++) {
                    if (i < inputLine.length) {
                        String hexString = Integer.toHexString(inputLine[i]);
                        env.write("0".repeat(2 - hexString.length()) + hexString.toUpperCase() + " ");
                    } else  {
                        env.write("   ");
                    }
                }

                env.write("| ");
                env.writeln(new String(inputLine));

                inputStream.close();
            }
        } catch (Exception ex) {
            env.writeln(ex.getMessage());
            return ShellStatus.CONTINUE;
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "hexdump";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>(List.of("""
                Command hexdump prints the given file in hexadecimal format.
                """.split("\n")));
        return Collections.unmodifiableList(commandDescription);
    }
}
