import java.util.ArrayList;
import java.util.Scanner;

public class Bruh {
    private static final String LINE = "  ____________________________________________________________\r\n   ";

    public static void echo() {
        Scanner scnr = new Scanner(System.in);
        String userInput = scnr.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            String response = LINE
                    + userInput + "\r\n" +
                    LINE;
            System.out.println(response);
            userInput = scnr.nextLine();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> listStrings = new ArrayList<>();
        boolean running = true;
        String greeting = "  ____________________________________________________________\r\n" +
                "   Hello! I'm Bruh\r\n" +
                "   What can I do for you?\r\n" +
                "  ____________________________________________________________\r\n";
        String farewell = "  ____________________________________________________________\r\n" +
                "   Bye. Hope to see you again soon!\r\n" +
                "  ____________________________________________________________\r\n";
        Scanner scnr = new Scanner(System.in);

        System.out.println(greeting);
        while (running) {
            String userInput = scnr.nextLine();
            switch (userInput.toLowerCase()) {
                case "bye":
                    running = false;
                    break;
                case "list":
                    String itemsString = "";
                    for (int i = 0; i < listStrings.size(); i++) {
                        itemsString += ((i + 1) + ". " + listStrings.get(i) + "\r\n   ");
                    }
                    System.out.println(LINE +
                            itemsString.trim() + "\r\n" +
                            LINE);
                    break;
                default:
                    String response = LINE +
                            "added: " + userInput + "\r\n" +
                            LINE;
                    listStrings.add(userInput);
                    System.out.println(response);
            }
        }
        System.out.println(farewell);
    }
}
