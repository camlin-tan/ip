package bruh.parser;

import bruh.command.Command;
import bruh.exception.BruhException;

/**
 * Parses command strings into Command objects.
 */
public class Parser {
    /**
     * Parses a command string into a Command object.
     *
     * @param commString The command string to parse.
     * @return A Command object representing the parsed command.
     * @throws BruhException If the command string is invalid.
     */
    public static Command parse(String commString) throws BruhException {
        String[] parts = commString.split(" ", 2);
        String commandType = parts[0].trim();
        String commandArgument = parts.length > 1 ? parts[1].trim() : "";
        return new Command(commandType, commandArgument);
    }
}
