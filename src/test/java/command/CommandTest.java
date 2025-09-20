package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bruh.command.Command;
import bruh.exception.BruhException;
import bruh.storage.Storage;
import bruh.task.TaskList;
import bruh.ui.Ui;

public class CommandTest {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        ui = new Ui();
        taskList = new TaskList();
        storage = new Storage("data/bruh.txt");
    }

    @Test
    public void execute_unknownCommand_exceptionThrown() {
        assertThrows(BruhException.class, () -> {
            new Command("invalid", "input");
        },
                "Throws exception for invalid input");
    }

    @Test
    public void execute_todoCommandValidDescription_addsTask() throws BruhException {
        assertEquals(0, taskList.size());

        Command command = new Command("todo", "read a book");
        command.execute(taskList, ui, storage);

        // Verify the state of the task list
        assertEquals(1, taskList.size());
    }

    @Test
    public void execute_deadlineCommandInvalidDate_addsTask() throws BruhException {
        assertEquals(0, taskList.size());

        Command command = new Command("deadline", "finish iP /by 3pm");
        BruhException exception = assertThrows(BruhException.class, () -> {
            command.execute(taskList, ui, storage);
        });

        assertEquals("ERROR!!! Invalid date format for deadline\r\n"
                + "Please use in form \'deadline {task-name} /by {time}\' and try again\r\n"
                + "Please use format of time: yyyy-MM-dd HH:mm (e.g. 2023-03-15 14:30)", exception.getMessage());
    }

    @Test
    public void execute_deadlineCommandValidDescription_addsTask() throws BruhException {
        Command command = new Command("todo", "finish iP /by 2025-08-29 16:00");
        command.execute(taskList, ui, storage);

        // Verify the state of the task list
        assertEquals(1, taskList.size());
    }

    @Test
    public void execute_markCommandEmptyArgument_exceptionThrow() throws BruhException {
        Command command = new Command("mark", "");
        BruhException exception = assertThrows(BruhException.class, () -> {
            command.execute(taskList, ui, storage);
        });

        assertEquals("u want mark what? air ah?\r\nPls use in form 'mark {task-number}' and try again",
                exception.getMessage());
    }

    @Test
    public void execute_markCommandInvalidArgument_exceptionThrow() throws BruhException {
        Command command = new Command("mark", "nonsense");
        BruhException exception = assertThrows(BruhException.class, () -> {
            command.execute(taskList, ui, storage);
        });

        assertEquals("Idk what u tryna mark...\r\nPls use in form \'mark {task-number}\' and try again",
                exception.getMessage());
    }
}
