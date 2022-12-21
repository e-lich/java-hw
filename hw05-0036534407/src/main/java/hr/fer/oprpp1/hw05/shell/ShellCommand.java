package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface ShellCommand defines a contract for a shell command.
 */
public interface ShellCommand {
    /**
     * @param env shell environment
     * @param arguments command arguments
     * @return shell status
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * @return command name
     */
    String getCommandName();

    /**
     * @return command description
     */
    List<String> getCommandDescription();
}
