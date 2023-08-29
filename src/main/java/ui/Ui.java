package ui;
import java.util.Scanner;

import tasks.Task;
import tasks.TaskList;

public class Ui {
    protected Scanner scanner;
    
    public Ui() {
        this.scanner = new Scanner(System.in);
    }
    
    public void showLoadingError() {
        System.out.println("Error loading file");
    }

    public void showWelcome() {
        String botName = "Dude";
        System.out.println("Hello! I'm " + botName);
        System.out.println("What can I do for you?");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.toString());
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showSchedule(String output) {
        if (output.equals("")) {
            System.out.println("There are no tasks on this date.");
        } else {
            System.out.println("Here are the tasks on this date:");
            System.out.println(output);
        }
    }

    public void showList(TaskList userTasks) {
        if (userTasks.size() == 0) {
            System.out.println("You have no tasks.");
        } else {
            System.out.println("Here are the tasks in your list:");
            System.out.println(userTasks.toString());
        }
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showInvalidCommandMessage() {
        System.out.println("Command is invalid. Please try again.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.toString());
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("Nice! I've marked this task as undone:");
        System.out.println(task.toString());
    }

    public void showSave() {
        System.out.println("Tasks saved to file.");
    }

    public void showLoad() {
        System.out.println("Tasks loaded from file.");
    }

    public void showFind(String output) {
        System.out.println("Here are the matching tasks in your list:");
        System.out.println(output);
    }
}
