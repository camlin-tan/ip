import java.util.ArrayList;
import java.util.Scanner;

public class Bruh {
    private static final String LINE = "  ____________________________________________________________\r\n   ";
    private static ArrayList<Task> listStrings = new ArrayList<>();

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

    public static void addTask(String userInput) {
        String response = LINE +
                "added: " + userInput + "\r\n" +
                LINE;
        listStrings.add(new Task(userInput));
        System.out.println(response);
    }

    public static void addTask(Task task) {
        listStrings.add(task);
        System.out.println(LINE +
                "Got it. I've added this task:\r\n   " +
                task + "\r\n   " +
                "Now you have " + listStrings.size() + " tasks in the list.\r\n" +
                LINE);
    }

    public static void main(String[] args) {
        boolean running = true;
        String greeting = LINE +
                "Hello! I'm Bruh\r\n   " +
                "What can I do for you?\r\n" +
                LINE;
        String farewell = LINE +
                "Bye. Hope to see you again soon!\r\n" +
                LINE;
        Scanner scnr = new Scanner(System.in);

        System.out.println(greeting);
        while (running) {
            String userInput = scnr.nextLine();
            String[] parts = userInput.split(" ", 2);
            String commandString = parts.length > 1 ? parts[0] : userInput;
            String commandArgument = parts.length > 1 ? parts[1] : "";
            if (commandString.equals("mark")) {
                try {
                    Integer index = commandArgument.trim() != "" ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
                    if (index >= 0 && index < listStrings.size()) {
                        Task task = listStrings.get(index);
                        task.markAsDone();
                        System.out.println(LINE +
                                "Nice! I've marked this task as done:\r\n   " +
                                task + "\r\n" +
                                LINE);
                    } else {
                        System.out.println(LINE +
                                "Invalid task number.\r\n" +
                                LINE);
                    }
                } catch (NumberFormatException e) {
                    addTask(userInput);
                }
            } else if (commandString.equals("unmark")) {
                try {
                    Integer index = commandArgument.trim() != "" ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
                    if (index >= 0 && index < listStrings.size()) {
                        Task task = listStrings.get(index);
                        task.markAsNotDone();
                        System.out.println(LINE +
                                "Sike! Task actually not done yet:\r\n   " +
                                task + "\r\n" +
                                LINE);
                    } else {
                        System.out.println(LINE +
                                "Invalid task number.\r\n" +
                                LINE);
                    }
                } catch (NumberFormatException e) {
                    addTask(userInput);
                }
            } else if (commandString.equals("todo")) {
                if (commandArgument.trim().isEmpty()) {
                    System.out.println(LINE +
                            "OOPS!!! The description of a todo cannot be empty.\r\n" +
                            LINE);
                } else {
                    Todo todo = new Todo(commandArgument.trim());
                    addTask(todo);
                }
            } else if (commandString.equals("deadline")) {
                String[] partsDeadline = commandArgument.split(" /by ", 2);
                if (commandArgument.trim().isEmpty() || partsDeadline.length < 2) {
                    System.out.println(LINE +
                            "OOPS!!! Invalid input for deadline\r\n" +
                            LINE);
                } else {
                    Deadline todo = new Deadline(partsDeadline[0].trim(), partsDeadline[1].trim());
                    addTask(todo);
                }
            } else if (commandString.equals("event")) {
                String[] eventParts = commandArgument.split(" /from ", 2);
                if (eventParts.length < 2) {
                    System.out.println(LINE +
                            "OOPS!!! Invalid input for event.\r\n" +
                            LINE);
                } else {
                    String[] timeParts = eventParts[1].split(" /to ", 2);
                    if (timeParts.length < 2) {
                        System.out.println(LINE +
                                "OOPS!!! The time range of an event is not specified correctly.\r\n" +
                                LINE);
                    } else {
                        Event event = new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                        listStrings.add(event);
                        addTask(event);
                    }
                }
            } else if (userInput.equalsIgnoreCase("bye")) {
                running = false;
                break;
            } else if (userInput.equalsIgnoreCase("list")) {
                String itemsString = "";
                for (int i = 0; i < listStrings.size(); i++) {
                    itemsString += ((i + 1) + ". " + listStrings.get(i) + "\r\n   ");
                }
                System.out.println(LINE +
                        itemsString.trim() + "\r\n" +
                        LINE);
            } else {
                addTask(userInput);
            }
        }
        System.out.println(farewell);
    }
}
