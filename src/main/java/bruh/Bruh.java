package bruh;

import bruh.command.Command;
import bruh.exception.BruhException;
import bruh.parser.Parser;
import bruh.storage.Storage;

import bruh.task.TaskList;
import bruh.ui.Ui;

public class Bruh {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Bruh(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (BruhException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main program loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                // ui.showLine(); // show the divider line ("_______")
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (BruhException e) {
                ui.showError(e.getMessage());
            } finally {
                // ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Bruh("data/tasks.txt").run();
    }
}
