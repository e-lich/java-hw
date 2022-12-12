package hr.fer.oprpp1.hw05.shell.commands;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

public class CharsetsShellCommand implements ShellCommand {
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Expected 0 arguments!");
            return ShellStatus.CONTINUE;
        }
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String key: charsets.keySet()) {
            env.writeln(key);
        }
        return ShellStatus.CONTINUE;
    }

    @Override
    public String getCommandName() {
        return "charsets";
    }

    @Override
    public List<String> getCommandDescription() {
        List<String> commandDescription = new ArrayList<>();
        commandDescription.add("Lists all available charsets.");
        return Collections.unmodifiableList(commandDescription);
    }
}
