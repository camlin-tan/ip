import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bruh {
    private static final String LINE = "  ____________________________________________________________\r\n   ";
    private static ArrayList<Task> listStrings = new ArrayList<>();

    public enum Command {
        TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, LIST, ECHO, BYE
    }

    public static void echo() {
        Scanner scnr = new Scanner(System.in);
        String userInput = scnr.nextLine();
        while (!userInput.equalsIgnoreCase("bye")) {
            String response = LINE + userInput + "\r\n" + LINE;
            System.out.println(response);
            userInput = scnr.nextLine();
        }
    }

    public static void saveTasksToHardDisk() {
        try {
            File taskFile = new File("./data/tasks.ser");
            taskFile.getParentFile().mkdirs(); // Create directories if they don't exist
            FileOutputStream fileOut = new FileOutputStream(taskFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(listStrings);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to hard disk: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadTasksFromHardDisk() {
        File taskFile = new File("./data/tasks.ser");
        if (!taskFile.exists()) {
            return; // No tasks to load
        }
        try {
            FileInputStream fileIn = new FileInputStream("./data/tasks.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            listStrings = (ArrayList<Task>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tasks from hard disk: " + e.getMessage());
        }
    }

    public static Command getCommand(String userInputCommand) {
        try {
            return Command.valueOf(userInputCommand.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Invalid command
        }
    }

    public static void addTask(String userInput) {
        String response = LINE + "added: " + userInput + "\r\n" + LINE;
        listStrings.add(new Task(userInput));
        System.out.println(response);
    }

    public static void addTask(Task task) {
        listStrings.add(task);
        System.out.println(LINE + "Got it. I've added this task:\r\n   " + task + "\r\n   " + "Now you have "
                + listStrings.size() + " tasks in the list.\r\n" + LINE);
    }

    public static void deleteTask(int index) throws BruhException {
        if (index >= 0 && index < listStrings.size()) {
            Task task = listStrings.remove(index);
            System.out.println(LINE + "Noted. I've removed this task:\r\n   " + task + "\r\n   " + "Now you have "
                    + listStrings.size() + " tasks in the list.\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void markTaskAsDone(int index) throws BruhException {
        if (index >= 0 && index < listStrings.size()) {
            Task task = listStrings.get(index);
            task.markAsDone();
            System.out.println(LINE + "Nice! I've marked this task as done:\r\n   " + task + "\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void markTaskAsNotDone(int index) throws BruhException {
        if (index >= 0 && index < listStrings.size()) {
            Task task = listStrings.get(index);
            task.markAsNotDone();
            System.out.println(LINE + "Sike! Task actually not done yet:\r\n   " + task + "\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void listTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(LINE + "No tasks in the list yet or for date specified.\r\n" + LINE);
        } else {
            String itemsString = "";
            for (int i = 0; i < tasks.size(); i++) {
                itemsString += ((i + 1) + ". " + tasks.get(i) + "\r\n   ");
            }
            System.out.println(LINE + itemsString.trim() + "\r\n" + LINE);
        }
    }

    public static ArrayList<Task> getTasksOnDate(LocalDate date) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task task : listStrings) {
            if (task instanceof Event) {
                Event event = (Event) task;
                if (event.start.toLocalDate().isBefore(date) && event.end.toLocalDate().isAfter(date)
                        || event.start.toLocalDate().equals(date) || event.end.toLocalDate().equals(date)) {
                    tasksOnDate.add(event);
                }
            } else if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.by.toLocalDate().equals(date)) {
                    tasksOnDate.add(deadline);
                }
            }
        }
        return tasksOnDate;
    }

    public static void main(String[] args) {
        boolean running = true;
        String greeting = LINE + "Hello! I'm Bruh\r\n   " + "What can I do for you?\r\n" + LINE;
        String farewell = LINE + "Bye. Hope to see you again soon!\r\n" + LINE;
        Scanner scnr = new Scanner(System.in);
        loadTasksFromHardDisk();

        System.out.println(greeting);
        while (running) {
            String userInput = scnr.nextLine();
            String[] parts = userInput.split(" ", 2);
            Command commandString = getCommand(parts[0].trim());
            String commandArgument = parts.length > 1 ? parts[1].trim() : "";
            try {
                if (commandString == null) {
                    throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n   "
                            + "todo, deadline, event, mark, unmark, list, bye");
                }
                switch (commandString) {
                case BYE:
                    running = false;
                    break;
                case LIST:
                    if (commandArgument.trim().isEmpty()) {
                        listTasks(listStrings);
                    } else {
                        try {
                            LocalDate date = LocalDate.parse(commandArgument.trim(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            ArrayList<Task> tasksOnDate = getTasksOnDate(date);
                            listTasks(tasksOnDate);
                        } catch (DateTimeParseException e) {
                            throw new BruhException(
                                    "Invalid date format\r\n   Pls use in form \'list {date}\' or \'list\' and try again\r\n   "
                                            + "Please use format of time: yyyy-MM-dd (e.g. 2023-03-15)");
                        }
                    }
                    break;
                case MARK:
                    if (commandArgument.trim().isEmpty()) {
                        throw new BruhException(
                                "u want mark what? air ah?\r\n   Pls use in form \'mark {task-number}\' and try again");
                    }
                    try {
                        Integer index = !commandArgument.isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
                        markTaskAsDone(index);
                    } catch (NumberFormatException e) {
                        throw new BruhException(
                                "Idk what u tryna mark...\r\n   Pls use in form \'mark {task-number}\' and try again");
                    }
                    break;
                case UNMARK:
                    if (commandArgument.trim().isEmpty()) {
                        throw new BruhException("ERROR!!! task number cannot be empty...\r\n   "
                                + "Pls use in form \'unmark {task-number}\' and try again");
                    }
                    try {
                        Integer index = !commandArgument.isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
                        markTaskAsNotDone(index);
                    } catch (NumberFormatException e) {
                        throw new BruhException(
                                "Idk what u tryna unmark... pls use in form \\'mark {task-number}\\' and try again");
                    }
                    break;
                case DELETE:
                    if (commandArgument.isEmpty()) {
                        throw new BruhException("ERROR!!! task number cannot be empty...\r\n   "
                                + "Pls use in form \'delete {task-number}\' and try again");
                    }
                    try {
                        Integer index = !commandArgument.isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
                        deleteTask(index);
                    } catch (NumberFormatException e) {
                        throw new BruhException(
                                "Idk what u tryna delete... pls use in form \\'delete {task-number}\\' and try again");
                    }
                    break;
                case TODO:
                    if (commandArgument.isEmpty()) {
                        throw new BruhException("ERROR!!! The description of a todo cannot be empty.\r\n   "
                                + "Please use in form \'todo {task-name}\' and try again");
                    } else {
                        Todo todo = new Todo(commandArgument);
                        addTask(todo);
                    }
                    break;
                case DEADLINE:
                    String[] partsDeadline = commandArgument.split(" /by ", 2);
                    if (commandArgument.isEmpty() || partsDeadline.length < 2) {
                        throw new BruhException("ERROR!!! Invalid format for deadline\r\n   "
                                + "Please use in form \'deadline {task-name} /by {time}\' and try again\r\n   "
                                + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
                    } else {
                        try {
                            LocalDateTime by = DateTimeParser.parse(partsDeadline[1].trim());
                            Deadline todo = new Deadline(partsDeadline[0].trim(), by);
                            addTask(todo);
                        } catch (DateTimeParseException e) {
                            throw new BruhException("ERROR!!! Invalid date format for deadline\r\n   "
                                    + "Please use in form \'deadline {task-name} /by {time}\' and try again\r\n   "
                                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
                        }
                    }
                    break;
                case EVENT:
                    String[] eventParts = commandArgument.split(" /from ", 2);
                    if (eventParts.length < 2) {
                        throw new BruhException("ERROR!!! Invalid input for event.\r\n   "
                                + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n   "
                                + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
                    } else {
                        String[] timeParts = eventParts[1].split(" /to ", 2);
                        if (timeParts.length < 2) {
                            throw new BruhException("ERROR!!! The start and end time not specified properly.\r\n   "
                                    + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n   "
                                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
                        } else {
                            try {
                                LocalDateTime from = DateTimeParser.parse(timeParts[0].trim());
                                LocalDateTime to = DateTimeParser.parse(timeParts[1].trim());
                                Event event = new Event(eventParts[0].trim(), from, to);
                                addTask(event);
                            } catch (DateTimeParseException e) {
                                throw new BruhException("ERROR!!! Invalid date format for event\r\n   "
                                        + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n   "
                                        + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
                            }
                        }
                    }
                    break;
                default:
                    throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n   "
                            + "todo, deadline, event, mark, unmark, list, bye");
                }
            } catch (BruhException e) {
                System.out.println(LINE + e.getMessage() + "\r\n" + LINE);
            }
        }
        saveTasksToHardDisk();
        System.out.println(farewell);
    }
}
