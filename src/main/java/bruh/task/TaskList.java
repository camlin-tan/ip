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

    /**
     * Constructs a new TaskList instance.
     *
     * @param tasks The list of tasks.
     */
    @SuppressWarnings("unchecked")
    public TaskList(Serializable tasks) {
        this.tasks = (ArrayList<Task>) tasks;
    }

    /**
     * Constructs a new TaskList instance.
     */
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

    /**
     * Returns the number of tasks in the list.
     * 
     * @return the number of tasks in the list
     */
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

    /**
     * Sorts the tasks by date. Events are sorted by their start date and Deadlines
     * by their due date. Todos remain in their original order.
     */
    public void sortTasksByDate() {
        tasks.sort((task1, task2) -> {
            if (task1 instanceof Event && task2 instanceof Event) {
                Event event1 = (Event) task1;
                Event event2 = (Event) task2;
                return event1.start.compareTo(event2.start);
            } else if (task1 instanceof Deadline && task2 instanceof Deadline) {
                Deadline deadline1 = (Deadline) task1;
                Deadline deadline2 = (Deadline) task2;
                return deadline1.by.compareTo(deadline2.by);
            } else if (task1 instanceof Event && task2 instanceof Deadline) {
                Event event = (Event) task1;
                Deadline deadline = (Deadline) task2;
                return event.start.compareTo(deadline.by);
            } else if (task1 instanceof Deadline && task2 instanceof Event) {
                Deadline deadline = (Deadline) task1;
                Event event = (Event) task2;
                return deadline.by.compareTo(event.start);
            } else {
                return 0; // Keep the original order for Todo tasks
            }
        });
    }

    /**
     * Sorts the tasks alphabetically by their description.
     */
    public void sortTasksByAlphabet() {
        tasks.sort((task1, task2) -> task1.getDescription().compareTo(task2.getDescription()));
    }

    /**
     * Returns the list of tasks.
     * 
     * @return the list of tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
