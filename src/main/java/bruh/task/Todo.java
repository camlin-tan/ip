package bruh.task;

public class Todo extends Task {
    /**
     * Constructs a new Todo instance.
     * 
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
