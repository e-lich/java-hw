package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;
import hr.fer.oprpp1.hw05.shell.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class LsShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] argsArray = Util.getSimpleCommandArgs(arguments);

        if (argsArray.length != 1) {
            env.writeln("Invalid number of arguments!");
            return ShellStatus.CONTINUE;
        }

        File dir = Path.of(argsArray[0]).toFile();
        if (!dir.isDirectory()) {
            env.writeln("A directory must be provided!");
            return ShellStatus.CONTINUE;
        }

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                StringBuilder fileInfo = new StringBuilder();

                fileInfo.append(getPermissions(file)).append(" ");

                String fileSize = String.valueOf(file.length());
                fileInfo.append(" ".repeat(10 - fileSize.length())).append(fileSize).append(" ");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                BasicFileAttributeView faView = Files.getFileAttributeView(
                        file.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
                BasicFileAttributes attributes;
                try {
                    attributes = faView.readAttributes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FileTime fileTime = attributes.creationTime();
                String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
                fileInfo.append(formattedDateTime).append(" ");

                fileInfo.append(file.getName());

                env.writeln(fileInfo.toString());
            }
        }

        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public List<String> getCommandDescription() {
        return null;
    }

    private static String getPermissions(File file) {
        StringBuilder permissions = new StringBuilder();

        if (file.isDirectory()) {
            permissions.append("d");
        } else {
            permissions.append("-");
        }

        if (file.canRead()) {
            permissions.append("r");
        } else {
            permissions.append("-");
        }

        if (file.canWrite()) {
            permissions.append("w");
        } else {
            permissions.append("-");
        }

        if (file.canExecute()) {
            permissions.append("x");
        } else {
            permissions.append("-");
        }

        return permissions.toString();
    }
}
