package bruh.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline with a due date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
    protected LocalDateTime by;

    /**
     * Constructs a new Deadline instance.
     * 
     * @param description description of deadline
     * @param by          due date of deadline
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        assert description != null : "Description should not be null";
        assert by != null : "Due date should not be null";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(FORMATTER) + ")";
    }
}
