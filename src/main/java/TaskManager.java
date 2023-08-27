import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> userTasks;
    protected static final DateTimeFormatter DATE_FORMAT_OUTPUT = DateTimeFormatter.ofPattern("d/M/yyyy");

    public TaskManager() {
        this.userTasks = new ArrayList<Task>();
    }

    public enum ActionType {
        BYE, LIST, SAVE, LOAD, MARK, UNMARK, DELETE, CLEAR, CLEARFILE, TODO, DEADLINE, EVENT, SCHEDULE;
    }

    public void handleAction(String input) throws DukeException{
        String[] inputArray = input.split(" ");
        String taskType = inputArray[0];
        try {
            switch (ActionType.valueOf(taskType.toUpperCase())) {
            case BYE:
                System.out.println("Bye. Hope to see you again soon!");
                break;
            case LIST:
                System.out.println("Here are the tasks in your list:");
                System.out.println(this.toString());
                break;
            case SAVE:
                this.saveToFile();
                break;
            case LOAD:
                this.loadFromFile();
                break;
            case MARK:
                this.markTaskAsDone(input);
                break;
            case UNMARK:
                this.markTaskAsUndone(input);
                break;
            case DELETE:
                this.delete(input);
                break;
            case CLEAR:
                this.clear();
                break;
            case CLEARFILE:
                Storage storage = new Storage();
                storage.clearFile();
                break;
            case SCHEDULE:
                this.getSchedule(input);
                break;
            default:
                Task task = Task.createTask(input);
                this.add(task);
            }
        } catch (IllegalArgumentException e) {
            throw new DukeException("Action is not recognised. Please use bye, list, mark, unmark, delete, todo, deadline or event.");
        }
    }
    public void add(Task task) {
        this.userTasks.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + userTasks.size() + " tasks in the list.");

    }

    public void delete(String input) throws DukeException {
        try {
            int taskID = Integer.parseInt(input.substring(7)) - 1;
            this.userTasks.remove(taskID);
            System.out.println("Noted. I've removed this task:");
            System.out.println(userTasks.get(taskID).toString());
            System.out.println("Now you have " + userTasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please ensure task exists.");
        }
    }

    public void clear() {
        this.userTasks.clear();
    }

    public void markTaskAsDone(String input) throws DukeException {
        try {
            int taskID = Integer.parseInt(input.substring(5)) - 1;
            this.userTasks.get(taskID).markAsDone();
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please ensure task exists.");
        }
    }

    public void markTaskAsUndone(String input) throws DukeException {
        try {
            int taskID = Integer.parseInt(input.substring(7)) - 1;
            this.userTasks.get(taskID).markAsUndone();
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid task number.");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Please ensure task exists.");
        }
    }

    public Task get(int taskID) {
        return this.userTasks.get(taskID);
    }

    public int size() {
        return this.userTasks.size();
    }

    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < userTasks.size(); i++) {
            output += (i + 1) + ". " + userTasks.get(i).toString() + "\n";
        }
        return output;
    }

    public String toFileString() {
        String output = "";
        for (int i = 0; i < userTasks.size(); i++) {
            output += userTasks.get(i).toFileString() + "\n";
        }
        return output;
    }

    public void saveToFile() throws DukeException{
        Storage storage  = new Storage();
        storage.saveStringToFile(this.toFileString());
    }

    public void loadFromFile() throws DukeException{
        Storage storage  = new Storage();
        String fileString = storage.loadStringFromFile();
        String[] fileStringArray = fileString.split("\n");
        if (userTasks.size() > 0) {
            throw new DukeException("Please clear your current task list before loading from file.");
        }
        for (int i = 0; i < fileStringArray.length; i++) {
            String[] taskStringArray = fileStringArray[i].split(" \\| ");
            String taskType = taskStringArray[0];
            Task task;
            switch (taskType) {
            case "T":
                task = new Todo();
                break;
            case "D":
                task = new Deadline();
                break;
            case "E":
                task = new Event();
                break;
            default:
                throw new DukeException("Corrupted file, ensure content is in format.");
            }
            task.fromFileString(fileStringArray[i]);
            this.add(task);
        }
    }

    public void getSchedule(String input) {
        LocalDate queryDateTime;
        System.out.println(input.substring(9));
        try {
            queryDateTime = LocalDate.parse(input.substring(9), DATE_FORMAT_OUTPUT);
        } catch (DateTimeException e) {
            System.out.println("Date should follow the format d/M/yyyy");
            return;
        }
        String output = "";
        for (int i = 0; i < userTasks.size(); i++) {
            Task task = userTasks.get(i);
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.by.toLocalDate().equals(queryDateTime)) {
                    output += (i + 1) + ". " + deadline.toString() + "\n";
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (!event.startDate.toLocalDate().isAfter(queryDateTime) & !event.endDate.toLocalDate().isBefore(queryDateTime)) {
                    output += (i + 1) + ". " + event.toString() + "\n";
                }
            }
        }
        if (output.equals("")) {
            System.out.println("There are no tasks on this date.");
        } else {
            System.out.println("Here are the tasks on this date:");
            System.out.println(output);
        }
    }
}
