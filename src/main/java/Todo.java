public class Todo {
    protected String description;

    public Todo(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString() + " (todo: " + description + ")";
    }
}
