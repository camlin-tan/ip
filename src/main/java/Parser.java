
public class Parser {
    public static Command parse(String commString) throws BruhException {
        String[] parts = commString.split(" ", 2);
        String commandType = parts[0].trim();
        String commandArgument = parts.length > 1 ? parts[1].trim() : "";
        return new Command(commandType, commandArgument);
    }
}
