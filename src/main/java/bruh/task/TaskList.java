package bruh.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import bruh.exception.BruhException;

public class TaskList implements Serializable {
    private ArrayList<Task> tasks;

    @SuppressWarnings("unchecked")
    public TaskList(Serializable tasks) {
        this.tasks = (ArrayList<Task>) tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        // System.out.println(LINE + "Got it. I've added this task:\r\n " + task + "\r\n
        // " + "Now you have "
        // + tasks.size() + " tasks in the list.\r\n" + LINE);
    }

    public int size() {
        return tasks.size();
    }

    public Task deleteTask(int index) throws BruhException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.remove(index);
            return task;
            // System.out.println(LINE + "Noted. I've removed this task:\r\n " + task +
            // "\r\n " + "Now you have "
            // + tasks.size() + " tasks in the list.\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public Task markTaskAsDone(int index) throws BruhException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markAsDone();
            // System.out.println(LINE + "Nice! I've marked this task as done:\r\n " + task
            // + "\r\n" + LINE);
            return task;
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public Task markTaskAsNotDone(int index) throws BruhException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markAsNotDone();
            return task;
            // System.out.println(LINE + "Sike! Task actually not done yet:\r\n " + task +
            // "\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public ArrayList<Task> getTasksOnDate(LocalDate date) {
        ArrayList<Task> tasksOnDate = new ArrayList<>();
        for (Task task : tasks) {
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
