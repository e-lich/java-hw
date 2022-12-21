package hr.fer.oprpp1.hw05.shell;

import java.util.ArrayList;

/**
 * Class Util contains utility methods for getting command arguments from a string.
 */
public class Util {
    /**
     * Parses arguments not containing paths from a string.
     * @param arguments unparsed string of arguments
     * @return array of parsed arguments
     */
    public static String[] getSimpleCommandArgs(String arguments) {
        return arguments.split(" ");
    }

    /**
     * Parses arguments containing paths from a string.
     * @param arguments unparsed string of arguments containing paths
     * @return array of parsed arguments
     */
    public static ArrayList<String> getPathArgs(String arguments) {
        ArrayList<String> argsArray = new ArrayList<>();
        char[] charArray = arguments.toCharArray();
        StringBuilder argument = new StringBuilder();


        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '"') {
                i++;
                while (i < charArray.length && charArray[i] != '"') {
                    // escape character "
                    if (charArray[i] == '\\' && i + 1 < charArray.length && charArray[i + 1] == '"') {
                        argument.append('"');
                        i++;
                    // escape character \
                    } else if (charArray[i] == '\\' && i + 1 < charArray.length && charArray[i + 1] == '\\') {
                        argument.append('\\');
                        i++;
                    } else {
                        argument.append(charArray[i]);
                    }
                    i++;
                }
                i++;
            } else {
                while (i < charArray.length && charArray[i] != ' ') {
                    argument.append(charArray[i]);
                    i++;
                }
            }
            argsArray.add(argument.toString());
            argument = new StringBuilder();
            if (i >= charArray.length) {
                break;
            }
        }
        return argsArray;
    }
}
