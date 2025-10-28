import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;

public class TaskTracker {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String DB_PATH = "/Users/stephenowusu/dev/java/projects/task-tracker/db.json";

    public static class Task {
        public int id;
        public String description;
        public String status;
        public String createdAt;
        public String updatedAt;
        public Task() {}
        public Task(int id, String description, String status){
            String currTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));;
            this.id=id;
            this.description=description;
            this.status=status;
            this.createdAt=currTime;
            this.updatedAt=currTime;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker App");
        System.out.println("Please type help to get the user guide");
        System.out.println("Arguments: " + Arrays.toString(args));
        String action = args[0]; // to do add try catch here
        File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/tasks.json");
        ObjectMapper test1 = new ObjectMapper();

        switch(action){
            case "add":
                add(args[1]);
                break;
            case "update":
                update(args[1], args[2]);
                break;
            case "delete":
                delete(args[1]);
                break;
            case "list":
                list(args[1]);
                break;
            default:
                help();
        }


    }

    private static void help() {
        System.out.println("This is the task tracker application");
        System.out.println("To add to the task: task-cli add <Task Title>");
        System.out.println("To update an existing task: task-cli update <task id> <task title>");
        System.out.println("To delete a task: task-cli delete <task id>");
        System.out.println("To list all tasks: task-cli list");
    }

    private static void add(String title) {
        try {
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");

            List<Task> taskList = file.exists() ? MAPPER.readValue(file, new TypeReference<List<Task>>() {
            }) : new ArrayList<>();
            int currCount = taskList.size();
            Task currTask = new Task(currCount + 1, title, "todo");
            taskList.add(currTask);
            MAPPER.writeValue(new File("/Users/stephenowusu/dev/java/projects/task-tracker/db.json"), taskList);
            System.out.println("Task added : " + title);
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to add : " + title);
        }
    }

    private static void update(String id, String title) {

    }

    private static void delete(String id) {

    }

    private static void list(String id) {

    }
}