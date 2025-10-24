import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

public class TaskTracker {
    public class Task {
        public int id;
        public String description;
        public String status;
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public Task(int id, String description, String status){
            LocalDateTime currTime = LocalDateTime.now();
            this.id=id;
            this.description=description;
            this.status=status;
            this.createdAt=currTime;
            this.updatedAt=currTime;
        }
    }
    private static int count = 1;
    public static void main(String[] args) {
        System.out.println("Welcome to Task Tracker App");
        System.out.println("Please type help to get the user guide");
        System.out.println("Arguments: " + Arrays.toString(args));
        String action = args[0] ? Null;
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
        File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/","db.json");
        ObjectMapper task = new ObjectMapper();
        List<Task> taskList = objectMapper.readValue(file, new TypeReference<List<Task>>(){});
        int currCount = taskList.size();
        Task currTask = new Task(currCount+1, title, "todo",);
        taskList.add(currTask);

        objectMapper.writeValue(new File("target/car.json"), car);


    }

    private static void update(String id, String title) {

    }

    private static void delete(String id) {

    }

    private static void list(String id) {

    }
}