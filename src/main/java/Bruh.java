import java.util.Scanner;

public class Bruh {

    public static void echo() {
        Scanner scnr = new Scanner(System.in);
        String userInput = scnr.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            String response = "  ____________________________________________________________\r\n   "
                    + userInput + "\r\n" +
                    "  ____________________________________________________________\r\n";
            System.out.println(response);
            userInput = scnr.nextLine();
        }
    }

    public static void main(String[] args) {
        String greeting = "  ____________________________________________________________\r\n" +
                "   Hello! I'm Bruh\r\n" +
                "   What can I do for you?\r\n" +
                "  ____________________________________________________________\r\n";
        String farewell = "  ____________________________________________________________\r\n" +
                "   Bye. Hope to see you again soon!\r\n" +
                "  ____________________________________________________________\r\n";
        System.out.println(greeting);
        echo();
        System.out.println(farewell);
    }
}
