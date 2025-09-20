package bruh.task;

import java.io.Serializable;

/**
 * Represents a task.
 */
public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task instance.
     *
     * @param description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the mark of the task (X if done, space if not done).
     *
     * @return mark
     */
    public String getMark() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + getMark() + "] " + description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }
}
