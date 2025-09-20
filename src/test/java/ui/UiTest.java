package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bruh.ui.Ui;

public class UiTest {

    private Ui ui;
    private Scanner customScanner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        customScanner = new Scanner("todo Buy potatoes\nmark 1\nbye\n");
        ui = new Ui(customScanner);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void listTasks_emptyList_success() {
        ui.listTasks(new ArrayList<>());
        assertEquals("No tasks in the list yet or for date specified.",
                outContent.toString().trim(),
                "Output print should be equal");
    }

    @Test
    public void testReadCommand() {
        String command = ui.readCommand();
        assertEquals("todo Buy potatoes", command);

        command = ui.readCommand();
        assertEquals("mark 1", command);

        command = ui.readCommand();
        assertEquals("bye", command);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
}
