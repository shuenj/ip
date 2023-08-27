import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Event extends Task {

    protected LocalDateTime startDate;
    protected LocalDateTime endDate;

    public Event(String description, String startDate, String endDate) throws DukeException {
        super(description);
        try {
            this.startDate = LocalDateTime.parse(startDate, Task.DATE_FORMAT);
            this.endDate = LocalDateTime.parse(endDate, Task.DATE_FORMAT);
        } catch (DateTimeException e) {
            throw new DukeException("Date should follow the format d/M/yyyy HHmm");
        }
    }

    public Event() {
        super("");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDate.format(Task.DATE_FORMAT_OUTPUT) + " to: " + endDate.format(Task.DATE_FORMAT_OUTPUT) + ")";
    }

    public String toFileString() {
        return "E | "  + super.getStatusIcon() + " | " + description + " | " + startDate.format(Task.DATE_FORMAT) + " | " + endDate.format(Task.DATE_FORMAT);
    }

    public void fromFileString(String fileString) throws DukeException {
        String[] fileStringArray = fileString.split(" \\| ");
        this.setStatusIcon(fileStringArray[1]);
        this.description = fileStringArray[2];
        try {
            this.startDate = LocalDateTime.parse(fileStringArray[3], Task.DATE_FORMAT);
            this.endDate = LocalDateTime.parse(fileStringArray[4], Task.DATE_FORMAT);
        } catch (DateTimeException e) {
            throw new DukeException("Date should follow the format d/M/yyyy HHmm");
        }
    }
}
