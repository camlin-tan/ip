package bruh.ui;

import java.util.ArrayList;
import java.util.Scanner;

import bruh.task.Task;

/**
 * User Interface for the application
 */
public class Ui {
    private static final String LINE = "  ____________________________________________________________\r\n   ";
    private Scanner scnr;

    public Ui() {
        this.scnr = new Scanner(System.in);
    }

    public Ui(Scanner scnr) {
        this.scnr = scnr;
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public String showWelcome() {
        System.out.println(LINE + "Hello! I'm Bruh");
        System.out.println("   What can I do for you?\r\n" + LINE);
        return LINE + "Hello! I'm Bruh   What can I do for you?\r\n" + LINE;
    }

    public String showFarewell() {
        System.out.println(LINE + "Bye. Hope to see you again soon!\r\n" + LINE);
        return LINE + "Bye. Hope to see you again soon!\r\n" + LINE;
    }

    public String listTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(LINE + "No tasks in the list yet or for date specified.\r\n" + LINE);
            return LINE + "No tasks in the list yet or for date specified.\r\n" + LINE;
        } else {
            String itemsString = "";
            for (int i = 0; i < tasks.size(); i++) {
                itemsString += ((i + 1) + ". " + tasks.get(i) + "\r\n   ");
            }
            System.out.println(LINE + itemsString.trim() + "\r\n" + LINE);
            return LINE + itemsString.trim() + "\r\n" + LINE;
        }
    }

    public String showError(String message) {
        System.out.println(LINE + message + "\r\n" + LINE);
        return LINE + message + "\r\n" + LINE;
    }

    public String showLoadingError(String message) {
        System.out.println("Error loading tasks from hard disk: " + message);
        return "Error loading tasks from hard disk: " + message;
    }

    public String showMessage(String message) {
        System.out.println(LINE + message + "\r\n" + LINE);
        return LINE + message + "\r\n" + LINE;
    }

    public String readCommand() {
        // System.out.print("Enter command: ");
        return scnr.nextLine();
    }
}
