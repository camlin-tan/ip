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

/**
 * Represents a command entered by the user.
 */
public class Command {
    private CommandType type;
    private String commandArgument;
    private boolean isExit;

    /**
     * Represents types of commands Bruh can take in
     */
    public enum CommandType {
        TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, LIST, BYE, FIND, SORT
    }

    /**
     * Constructs a new Command instance.
     *
     * @param type            type of command
     * @param commandArgument argument of command
     * @throws BruhException
     */
    public Command(String type, String commandArgument) throws BruhException {
        try {
            this.type = CommandType.valueOf(type.toUpperCase());
            this.commandArgument = commandArgument;
        } catch (IllegalArgumentException e) {
            throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n"
                    + "todo, deadline, event, mark, unmark, list, bye, find, sort");
        }
    }

    /**
     * Returns the type of command.
     *
     * @return type the type of command
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Returns the command argument.
     * 
     * @return commandArgument
     */
    public String getCommandArgument() {
        return commandArgument;
    }

    /**
     * Checks if the command is an exit command.
     *
     * @return True if the command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Executes the list command, listing all tasks in tasklist.
     * 
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeListCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        if (commandArgument.trim().isEmpty()) {
            outputString = ui.listTasks(tasks.getTasks());
        } else {
            try {
                LocalDate date = LocalDate.parse(commandArgument.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                ArrayList<Task> tasksOnDate = tasks.getTasksOnDate(date);
                outputString = ui.listTasks(tasksOnDate);
            } catch (DateTimeParseException e) {
                throw new BruhException(
                        "Invalid date format\r\nPls use in form \'list {date}\' or \'list\' and try again\r\n"
                                + "Please use format of time: yyyy-MM-dd (e.g. 2023-03-15)");
            }
        }
        return outputString;
    }

    /**
     * Executes the mark command, marking a task as done.
     * 
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeMarkCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        if (commandArgument.trim().isEmpty()) {
            throw new BruhException(
                    "u want mark what? air ah?\r\nPls use in form \'mark {task-number}\' and try again");
        }
        try {
            Integer index = !commandArgument.isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
            Task task = tasks.markTaskAsDone(index);
            outputString = ui.showMessage("Nice! I've marked this task as done:\r\n" + task);
        } catch (NumberFormatException e) {
            throw new BruhException("Idk what u tryna mark...\r\nPls use in form \'mark {task-number}\' and try again");
        }
        return outputString;
    }

    /**
     * Executes the unmark command, marking a task as not done.
     *
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeUnmarkCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        if (commandArgument.trim().isEmpty()) {
            throw new BruhException("ERROR!!! task number cannot be empty...\r\n"
                    + "Pls use in form \'unmark {task-number}\' and try again");
        }
        try {
            Integer index = !commandArgument.isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
            Task task = tasks.markTaskAsNotDone(index);
            outputString = ui.showMessage("Sike! Task actually not done yet:\r\n" + task);
        } catch (NumberFormatException e) {
            throw new BruhException(
                    "Idk what u tryna unmark... pls use in form \\'mark {task-number}\\' and try again");
        }
        return outputString;
    }

    /**
     * Executes the delete command, deleting a task from tasklist.
     *
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeDeleteCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        if (commandArgument.isEmpty()) {
            throw new BruhException("ERROR!!! task number cannot be empty...\r\n"
                    + "Pls use in form \'delete {task-number}\' and try again");
        }
        try {
            Integer index = !commandArgument.isEmpty() ? Integer.parseInt(commandArgument.trim()) - 1 : -1;
            Task deletedTask = tasks.deleteTask(index);
            outputString = ui.showMessage("Noted. I've removed this task:\r\n" + deletedTask + "\r\n" + "Now you have "
                    + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new BruhException(
                    "Idk what u tryna delete... pls use in form \\'delete {task-number}\\' and try again");
        }
        return outputString;
    }

    /**
     * Executes the find command, finding tasks that match the keyword.
     * 
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeFindCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        String keyword = commandArgument.trim();
        ArrayList<Task> matchingTasks = tasks.getTasksByKeyword(keyword);
        if (matchingTasks.isEmpty()) {
            outputString = ui.showMessage("No tasks matching keyword: " + keyword);
        } else {
            outputString = ui.showMessage("Here are the matching tasks in your list:");
            outputString += ui.listTasks(matchingTasks);
        }
        return outputString;
    }

    /**
     * Executes the todo command, adding a todo task to tasklist.
     *
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeTodoCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        if (commandArgument.isEmpty()) {
            throw new BruhException("ERROR!!! The description of a todo cannot be empty.\r\n"
                    + "Please use in form \'todo {task-name}\' and try again");
        } else {
            Todo todo = new Todo(commandArgument);
            tasks.addTask(todo);
            outputString = ui.showMessage("Got it. I've added this task:\r\n" + todo + "\r\n" + "Now you have "
                    + tasks.size() + " tasks in the list.");
        }
        return outputString;
    }

    public String executeSortCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        if (commandArgument.isEmpty()) {
            throw new BruhException("ERROR!!! Invalid sort command format.\r\n"
                    + "Please use \'sort date\' or \'sort alphabet\' and try again");
        }
        if (commandArgument.equals("date")) {
            tasks.sortTasksByDate();
            outputString = ui.showMessage("Tasks have been sorted by date.");
        } else if (commandArgument.equals("alphabet")) {
            tasks.sortTasksByAlphabet();
            outputString = ui.showMessage("Tasks have been sorted alphabetically.");
        } else {
            throw new BruhException("ERROR!!! Invalid sort command format.\r\n"
                    + "Please use \'sort date\' or \'sort alphabet\' and try again");
        }
        return outputString;
    }

    /**
     * Executes the deadline command, adding a deadline task to tasklist.
     * 
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeDeadlineCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        String[] partsDeadline = commandArgument.split(" /by ", 2);
        if (commandArgument.isEmpty() || partsDeadline.length < 2) {
            throw new BruhException("ERROR!!! Invalid format for deadline\r\n"
                    + "Please use in form \'deadline {task-name} /by {time}\' and try again\r\n"
                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
        }
        try {
            LocalDateTime by = DateTimeParser.parse(partsDeadline[1].trim());
            Deadline deadline = new Deadline(partsDeadline[0].trim(), by);
            tasks.addTask(deadline);
            outputString = ui.showMessage("Got it. I've added this task:\r\n" + deadline + "\r\n" + "Now you have "
                    + tasks.size() + " tasks in the list.");
        } catch (DateTimeParseException e) {
            throw new BruhException("ERROR!!! Invalid date format for deadline\r\n"
                    + "Please use in form \'deadline {task-name} /by {time}\' and try again\r\n"
                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
        }
        return outputString;
    }

    /**
     * Executes the event command, adding an event task to tasklist.
     *
     * @param tasks the list of tasks
     * @param ui    the user interface
     * @return outputString the output message after executing the command
     * @throws BruhException
     */
    public String executeEventCommand(TaskList tasks, Ui ui) throws BruhException {
        String outputString = "";
        String[] eventParts = commandArgument.split(" /from ", 2);
        if (eventParts.length < 2) {
            throw new BruhException("ERROR!!! Invalid input for event.\r\n"
                    + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n"
                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
        }
        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2) {
            throw new BruhException("ERROR!!! The start and end time not specified properly.\r\n"
                    + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n"
                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
        }
        try {
            LocalDateTime from = DateTimeParser.parse(timeParts[0].trim());
            LocalDateTime to = DateTimeParser.parse(timeParts[1].trim());
            Event event = new Event(eventParts[0].trim(), from, to);
            tasks.addTask(event);
            outputString = ui.showMessage("Got it. I've added this task:\r\n" + event + "\r\n" + "Now you have "
                    + tasks.size() + " tasks in the list.");
        } catch (DateTimeParseException e) {
            throw new BruhException("ERROR!!! Invalid date format for event\r\n"
                    + "Please use in form \'event {task-name} /from {start-time} /to {end-time}\' and try again\r\n"
                    + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)");
        }
        return outputString;
    }

    /**
     * Executes the command.
     *
     * @param tasks   The list of tasks to operate on.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving/loading tasks.
     * @return outputString The output message after executing the command.
     * @throws BruhException If command input has invalid format.
     */
    public String execute(TaskList tasks, Ui ui, Storage storage) throws BruhException {
        assert tasks != null : "TaskList should not be null";
        assert ui != null : "Ui should not be null";
        assert storage != null : "Storage should not be null";
        assert type != null : "Command type should not be null";
        String outputString = "";
        if (type == null) {
            throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n"
                    + "todo, deadline, event, mark, unmark, list, bye, find, sort");
        }
        switch (type) {
        case BYE:
            outputString = ui.showFarewell();
            isExit = true;
            break;
        case LIST:
            outputString = executeListCommand(tasks, ui);
            break;
        case MARK:
            outputString = executeMarkCommand(tasks, ui);
            break;
        case UNMARK:
            outputString = executeUnmarkCommand(tasks, ui);
            break;
        case DELETE:
            outputString = executeDeleteCommand(tasks, ui);
            break;
        case TODO:
            outputString = executeTodoCommand(tasks, ui);
            break;
        case DEADLINE:
            outputString = executeDeadlineCommand(tasks, ui);
            break;
        case EVENT:
            outputString = executeEventCommand(tasks, ui);
            break;
        case FIND:
            outputString = executeFindCommand(tasks, ui);
            break;
        case SORT:
            outputString = executeSortCommand(tasks, ui);
            break;
        default:
            throw new BruhException("Idk what u tryna say, pls try again with one of the commands:\r\n"
                    + "todo, deadline, event, mark, unmark, list, bye, find, sort");
        }
        storage.save(tasks.getTasks());
        return outputString;
    }
}
