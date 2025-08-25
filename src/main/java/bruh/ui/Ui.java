package bruh.ui;

import java.util.ArrayList;
import java.util.Scanner;

import bruh.task.Task;

public class Ui {
    private static final String LINE = "  ____________________________________________________________\r\n   ";
    private Scanner scnr = new Scanner(System.in);

    public void showLine() {
        System.out.println(LINE);
    }

    public void showWelcome() {
        System.out.println(LINE + "Hello! I'm Bruh");
        System.out.println("   What can I do for you?\r\n" + LINE);
    }

    public void showFarewell() {
        System.out.println(LINE + "Bye. Hope to see you again soon!\r\n" + LINE);
    }

    public void listTasks(ArrayList<Task> tasks) {
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

    public void showError(String message) {
        System.out.println(LINE + message + "\r\n" + LINE);
    }

    public void showLoadingError(String message) {
        System.out.println("Error loading tasks from hard disk: " + message);
    }

    public void showMessage(String message) {
        System.out.println(LINE + message + "\r\n" + LINE);
    }

    public String readCommand() {
        // System.out.print("Enter command: ");
        return scnr.nextLine();
    }
}
