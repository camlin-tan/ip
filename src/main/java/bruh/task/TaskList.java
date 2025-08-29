package bruh.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import bruh.exception.BruhException;

/**
 * Represents a list of tasks.
 */
public class TaskList implements Serializable {
    private ArrayList<Task> tasks;

    @SuppressWarnings("unchecked")
    public TaskList(Serializable tasks) {
        this.tasks = (ArrayList<Task>) tasks;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a Task to the list.
     * 
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        // System.out.println(LINE + "Got it. I've added this task:\r\n " + task + "\r\n
        // " + "Now you have "
        // + tasks.size() + " tasks in the list.\r\n" + LINE);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Deletes a Task from the list.
     *
     * @param index The index of the task to delete.
     * @return The deleted task.
     * @throws BruhException If the task index is invalid.
     */
    public Task deleteTask(int index) throws BruhException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.remove(index);
            return task;
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    /**
     * Marks a task as done.
     * 
     * @param index The index of the task to mark as done.
     * @return The marked task.
     * @throws BruhException If the task index is invalid.
     */
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

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to mark as not done.
     * @return The unmarked task.
     * @throws BruhException If the task index is invalid.
     */
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

    /**
     * Returns the events ongoing on a specific date and deadlines due on that
     * specific date.
     *
     * @param date The date to check.
     * @return A list of tasks on the specified date.
     */
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

    /**
     * Returns a list of tasks that match the given keyword.
     * 
     * @param keyword the keyword to search for
     * @return a list of tasks that match the keyword
     */
    public ArrayList<Task> getTasksByKeyword(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
