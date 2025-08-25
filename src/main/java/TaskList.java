import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaskList implements Serializable {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void markTaskAsDone(int index) throws BruhException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markAsDone();
            // System.out.println(LINE + "Nice! I've marked this task as done:\r\n " + task
            // + "\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public void markTaskAsNotDone(int index) throws BruhException {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markAsNotDone();
            // System.out.println(LINE + "Sike! Task actually not done yet:\r\n " + task +
            // "\r\n" + LINE);
        } else {
            throw new BruhException("ERROR!!! invalid task number... try again");
        }
    }

    public static void listTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            // System.out.println(LINE + "No tasks in the list yet or for date
            // specified.\r\n" + LINE);
        } else {
            String itemsString = "";
            for (int i = 0; i < tasks.size(); i++) {
                itemsString += ((i + 1) + ". " + tasks.get(i) + "\r\n   ");
            }
            // System.out.println(LINE + itemsString.trim() + "\r\n" + LINE);
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
