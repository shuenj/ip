import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> userTasks;
    protected static final DateTimeFormatter DATE_FORMAT_OUTPUT = DateTimeFormatter.ofPattern("d/M/yyyy");

    public TaskList() {
        this.userTasks = new ArrayList<Task>();
    }
    
    public TaskList(ArrayList<Task> userTasks) {
        this.userTasks = userTasks;
    }

    // public enum ActionType {
    //     BYE, LIST, SAVE, LOAD, MARK, UNMARK, DELETE, CLEAR, CLEARFILE, TODO, DEADLINE, EVENT, SCHEDULE;
    // }

    // public void handleAction(String input) throws DukeException{
    //     String[] inputArray = input.split(" ");
    //     String taskType = inputArray[0];
    //     try {
    //         switch (ActionType.valueOf(taskType.toUpperCase())) {
    //         case BYE:
    //             System.out.println("Bye. Hope to see you again soon!");
    //             break;
    //         case LIST:
    //             System.out.println("Here are the tasks in your list:");
    //             System.out.println(this.toString());
    //             break;
    //         case SAVE:
    //             this.saveToFile();
    //             break;
    //         case LOAD:
    //             this.loadFromFile();
    //             break;
    //         case MARK:
    //             this.markTaskAsDone(input);
    //             break;
    //         case UNMARK:
    //             this.markTaskAsUndone(input);
    //             break;
    //         case DELETE:
    //             this.delete(input);
    //             break;
    //         case CLEAR:
    //             this.clear();
    //             break;
    //         case CLEARFILE:
    //             Storage storage = new Storage();
    //             storage.clearFile();
    //             break;
    //         case SCHEDULE:
    //             this.getSchedule(input);
    //             break;
    //         default:
    //             Task task = Task.createTask(input);
    //             this.add(task);
    //         }
    //     } catch (IllegalArgumentException e) {
    //         throw new DukeException("Action is not recognised. Please use bye, list, mark, unmark, delete, todo, deadline or event.");
    //     }
    // }
    public void add(Task task) {
        this.userTasks.add(task);

    }

    public void delete(int taskId) {
        this.userTasks.remove(taskId);
    }

    public void clear() {
        this.userTasks.clear();
    }

    public void mark(int taskId) throws DukeException {
        this.userTasks.get(taskId).markAsDone();
    }

    public void unmark(int taskId) throws DukeException {
        this.userTasks.get(taskId).markAsUndone();
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

    // public String toFileString() {
    //     String output = "";
    //     for (int i = 0; i < userTasks.size(); i++) {
    //         output += userTasks.get(i).toFileString() + "\n";
    //     }
    //     return output;
    // }

    // public void saveToFile() throws DukeException{
    //     Storage storage  = new Storage();
    //     storage.saveStringToFile(this.toFileString());
    // }

    // public void loadFromFile() throws DukeException{
    //     Storage storage  = new Storage();
    //     String fileString = storage.loadStringFromFile();

    // }

    // public void getSchedule(String input) throws DukeException {
    // }

}
