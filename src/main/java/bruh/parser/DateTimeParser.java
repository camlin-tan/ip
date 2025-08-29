package bruh.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parses date and time strings into LocalDateTime objects.
 */
public class DateTimeParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Parses a date and time string into a LocalDateTime object.
     * 
     * @param dateTimeString The date and time string to parse.
     * @return The parsed LocalDateTime object.
     * @throws DateTimeParseException If the date and time string is invalid.
     */
    public static LocalDateTime parse(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, FORMATTER);
    }
}
