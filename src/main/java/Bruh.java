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

    public static void deleteTask(int index) throws BruhException {
        if (index >= 0 && index < listStrings.size()) {
            Task task = listStrings.remove(index);
            System.out.println(LINE +
                    "Noted. I've removed this task:\r\n   " +
                    task + "\r\n   " +
                    "Now you have " + listStrings.size() + " tasks in the list.\r\n" +
                    LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void markTaskAsDone(int index) throws BruhException {
        if (index >= 0 && index < listStrings.size()) {
            Task task = listStrings.get(index);
            task.markAsDone();
            System.out.println(LINE +
                    "Nice! I've marked this task as done:\r\n   " +
                    task + "\r\n" +
                    LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void markTaskAsNotDone(int index) throws BruhException {
        if (index >= 0 && index < listStrings.size()) {
            Task task = listStrings.get(index);
            task.markAsNotDone();
            System.out.println(LINE +
                    "Sike! Task actually not done yet:\r\n   " +
                    task + "\r\n" +
                    LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void listTasks() {
        if (listStrings.isEmpty()) {
            System.out.println(LINE + "No tasks in the list yet.\r\n" + LINE);
        } else {
            String itemsString = "";
            for (int i = 0; i < listStrings.size(); i++) {
                itemsString += ((i + 1) + ". " + listStrings.get(i) + "\r\n   ");
            }
            System.out.println(LINE +
                    itemsString.trim() + "\r\n" +
                    LINE);
        }
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
            try {
                if (commandString.equals("mark")) {
                    if (commandArgument.trim().isEmpty()) {
                        throw new BruhException(
                                "u want mark what? air ah?\r\n   Pls use in form \'mark {task-number}\' and try again");
                    }
                    try {
                        Integer index = !commandArgument.trim().isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1
                                : -1;
                        markTaskAsDone(index);
                    } catch (NumberFormatException e) {
                        throw new BruhException(
                                "Idk what u tryna mark...\r\n   Pls use in form \'mark {task-number}\' and try again");
                    }
                } else if (commandString.equals("unmark")) {
                    if (commandArgument.trim().isEmpty()) {
                        throw new BruhException(
                                "ERROR!!! task number cannot be empty...\r\n   "
                                        + "Pls use in form \'unmark {task-number}\' and try again");
                    }
                    try {
                        Integer index = commandArgument.trim() != "" ? Integer.parseInt(commandArgument.trim()) - 1
                                : -1;
                        markTaskAsNotDone(index);
                    } catch (NumberFormatException e) {
                        throw new BruhException(
                                "Idk what u tryna unmark... pls use in form \\'mark {task-number}\\' and try again");
                    }
                } else if (commandString.equals("delete")) {
                    if (commandArgument.trim().isEmpty()) {
                        throw new BruhException(
                                "ERROR!!! task number cannot be empty...\r\n   "
                                        + "Pls use in form \'delete {task-number}\' and try again");
                    }
                    try {
                        Integer index = commandArgument.trim() != "" ? Integer.parseInt(commandArgument.trim()) - 1
                                : -1;
                        deleteTask(index);
                    } catch (NumberFormatException e) {
                        throw new BruhException(
                                "Idk what u tryna delete... pls use in form \\'delete {task-number}\\' and try again");
                    }
                } else if (commandString.equals("todo")) {
                    if (commandArgument.trim().isEmpty()) {
                        throw new BruhException("ERROR!!! The description of a todo cannot be empty.\r\n   " +
                                "Please use in form \'todo {task-name}\' and try again");
                    } else {
                        Todo todo = new Todo(commandArgument.trim());
                        addTask(todo);
                    }
                } else if (commandString.equals("deadline")) {
                    String[] partsDeadline = commandArgument.split(" /by ", 2);
                    if (commandArgument.trim().isEmpty() || partsDeadline.length < 2) {
                        throw new BruhException("ERROR!!! Invalid input for deadline\r\n   " +
                                "Please use in form \'deadline {task-name} /by {time}\' and try again");
                    } else {
                        Deadline todo = new Deadline(partsDeadline[0].trim(), partsDeadline[1].trim());
                        addTask(todo);
                    }
                } else if (commandString.equals("event")) {
                    String[] eventParts = commandArgument.split(" /from ", 2);
                    if (eventParts.length < 2) {
                        throw new BruhException("ERROR!!! Invalid input for event.\r\n   " +
                                "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again");
                    } else {
                        String[] timeParts = eventParts[1].split(" /to ", 2);
                        if (timeParts.length < 2) {
                            throw new BruhException(
                                    "ERROR!!! The start and end time not specified properly.\r\n   " +
                                            "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again");
                        } else {
                            Event event = new Event(eventParts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
                            addTask(event);
                        }
                    }
                } else if (userInput.equalsIgnoreCase("bye")) {
                    running = false;
                    break;
                } else if (userInput.equalsIgnoreCase("list")) {
                    listTasks();
                } else {
                    throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n   " +
                            "todo, deadline, event, mark, unmark, list, bye");
                }
            } catch (BruhException e) {
                System.out.println(LINE + e.getMessage() + "\r\n" + LINE);
            }
        }
        System.out.println(farewell);
    }
}
