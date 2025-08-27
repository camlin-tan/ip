package bruh.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import bruh.exception.BruhException;
import bruh.parser.DateTimeParser;
import bruh.storage.Storage;
import bruh.task.Deadline;
import bruh.task.Event;
import bruh.task.Task;
import bruh.task.TaskList;
import bruh.task.Todo;
import bruh.ui.Ui;

public class Command {
    private CommandType type;
    private String commandArgument;
    private boolean exit;

    public enum CommandType {
        TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, LIST, BYE, FIND
    }

    public Command(String type, String commandArgument) throws BruhException {
        try {
            this.type = CommandType.valueOf(type.toUpperCase());
            this.commandArgument = commandArgument;
        } catch (IllegalArgumentException e) {
            throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n   "
                    + "todo, deadline, event, mark, unmark, list, bye");
        }
    }

    public CommandType getType() {
        return type;
    }

    public String getCommandArgument() {
        return commandArgument;
    }

    public boolean isExit() {
        return exit;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws BruhException {
        if (type == null) {
            throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n   "
                    + "todo, deadline, event, mark, unmark, list, bye");
        }
        switch (type) {
        case BYE:
            ui.showFarewell();
            exit = true;
            break;
        case LIST:
            if (commandArgument.trim().isEmpty()) {
                ui.listTasks(tasks.getTasks());
            } else {
                try {
                    LocalDate date = LocalDate.parse(commandArgument.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    ArrayList<Task> tasksOnDate = tasks.getTasksOnDate(date);
                    ui.listTasks(tasksOnDate);
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
                Task task = tasks.markTaskAsDone(index);
                ui.showMessage("Nice! I've marked this task as done:\r\n   " + task);
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
                Task task = tasks.markTaskAsNotDone(index);
                ui.showMessage("Sike! Task actually not done yet:\r\n   " + task);
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
                Task deletedTask = tasks.deleteTask(index);
                ui.showMessage("Noted. I've removed this task:\r\n   " + deletedTask + "\r\n   " + "Now you have "
                        + tasks.size() + " tasks in the list.");
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
                tasks.addTask(todo);
                ui.showMessage("Got it. I've added this task:\r\n   " + todo + "\r\n   " + "Now you have "
                        + tasks.size() + " tasks in the list.");
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
                    tasks.addTask(todo);
                    ui.showMessage("Got it. I've added this task:\r\n   " + todo + "\r\n   " + "Now you have "
                            + tasks.size() + " tasks in the list.");
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
                        tasks.addTask(event);
                        ui.showMessage("Got it. I've added this task:\r\n   " + event + "\r\n   " + "Now you have "
                                + tasks.size() + " tasks in the list.");
                    } catch (DateTimeParseException e) {
                        throw new BruhException("ERROR!!! Invalid date format for event\r\n   "
                                + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n   "
                                + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
                    }
                }
            }
            break;
        case FIND:
            String keyword = commandArgument.trim();
            ArrayList<Task> matchingTasks = tasks.getTasksByKeyword(keyword);
            if (matchingTasks.isEmpty()) {
                ui.showMessage("No tasks matching keyword: " + keyword);
            } else {
                ui.showMessage("Here are the matching tasks in your list:");
                ui.listTasks(matchingTasks);
            }
            break;
        default:
            throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n   "
                    + "todo, deadline, event, mark, unmark, list, bye");
        }
        storage.save(tasks.getTasks());
    }
}