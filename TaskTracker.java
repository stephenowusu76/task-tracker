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
            String currTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.id=id;
            this.description=description;
            this.status=status;
            this.createdAt=currTime;
            this.updatedAt=currTime;
        }

        @Override
        public String toString(){
            return new StringBuilder()
                    .append(id).append(" ")
                    .append(description).append(" ")
                    .append(status).append(" ")
                    .append(createdAt).append(" ")
                    .append(updatedAt).toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("Arguments: " + Arrays.toString(args));
        String action;
        if (args.length>=1){
            action = args[0];
        }else{
            help();
            return;
        }

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
            case "mark-in-progress":
                markProgress(args[1], "in-progress");
                break;
            case "mark-done":
                markProgress(args[1], "done");
                break;
            case "list":
                if (args.length>1){
                    list (args[1]);
                } else {
                    list();
                }
                break;
            default:
                help();
        }
    }

    private static void help() {
        System.out.println("This is the task tracker application");
        System.out.println("To add to the task: task-cli add <Task Title>");
        System.out.println("To update an existing task: task-cli update <task id> <task title>");
        System.out.println("To mark a task to in-progress: task-cli mark-in-progress <task id>");
        System.out.println("To mark a task to done: task-cli mark-done <task id>");
        System.out.println("To delete a task: task-cli delete <task id>");
        System.out.println("To list all tasks: task-cli list");
        System.out.println("To list all tasks: task-cli list <status>{todo, done, in-progress}");
    }

    private static void add(String title) {
        try {
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");

            ArrayList<Task> taskList = file.exists() ? MAPPER.readValue(file, new TypeReference<ArrayList<Task>>() {
            }) : new ArrayList<>();
            int currCount = taskList.size()==0 ? 0 : taskList.get(taskList.size()-1).id;// we need to solve for the issue of when two objs might have the same id
            Task currTask = new Task(currCount + 1, title, "todo");
            taskList.add(currTask);
            MAPPER.writeValue(new File("/Users/stephenowusu/dev/java/projects/task-tracker/db.json"), taskList);
            System.out.println("Task added : " + title + " id "+ currTask.id);
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to add : " + title);
        }
    }

    private static void update(String id, String title) {
        try {
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");
            ArrayList<Task> taskList;
            if (file.exists()){
                taskList = MAPPER.readValue(file, new TypeReference<ArrayList<Task>>() {});
            } else {
                System.out.println("No tasks to update");
                return;
            }
            int index = Integer.parseInt(id);
            for(Task currTask : taskList){
                if(currTask.id == index){
                    currTask.description = title;
                    currTask.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    break;
                }
            }
            MAPPER.writeValue(new File(DB_PATH), taskList);
            System.out.println("Task updated : " + title);
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to update : " + title);
        }
    }

    private static void delete(String id) {
        try {
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");
            ArrayList<Task> taskList;
            if (file.exists()){
                taskList = MAPPER.readValue(file, new TypeReference<ArrayList<Task>>() {});
            } else {
                System.out.println("No tasks to delete");
                return;
            }
            int index = Integer.parseInt(id);
            for(int i=0; i<taskList.size(); i++){
                Task currTask = taskList.get(i);
                if(currTask.id == index){
                    taskList.remove(i);
                    break;
                }
            }
            MAPPER.writeValue(new File(DB_PATH), taskList);
            System.out.println("Task deleted : " + id);
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to delete : " + id);
        }
    }

    private static void list() {
        try {
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");
            ArrayList<Task> taskList;
            if (file.exists()){
                taskList = MAPPER.readValue(file, new TypeReference<ArrayList<Task>>() {});
            } else {
                System.out.println("No tasks to list");
                return;
            }
            for(Task currTask : taskList){
                System.out.println(currTask.toString());
            }
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to list tasks");
        }
    }

    private static void list(String status) {
        try {
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");
            ArrayList<Task> taskList;
            if (file.exists()){
                taskList = MAPPER.readValue(file, new TypeReference<ArrayList<Task>>() {});
            } else {
                System.out.println("No tasks to list");
                return;
            }
            for(Task currTask : taskList){
                if(currTask.status.equals(status)){
                    System.out.println(currTask.toString());
                }
            }
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to list tasks in" + status);
        }
    }

    private static void markProgress(String id, String status) {
        int idx = Integer.parseInt(id);
        try{
            File file = new File("/Users/stephenowusu/dev/java/projects/task-tracker/", "db.json");
            ArrayList<Task> taskList;
            if(file.exists()){
                taskList = MAPPER.readValue(file, new TypeReference<ArrayList<Task>>() {});
            } else {
                System.out.println("No tasks to update");
                return;
            }
            for(int i=0; i<taskList.size(); i++){
                Task currTask = taskList.get(i);
                if (currTask.id==idx){
                    currTask.status = status;
                    break;
                }
            }
            MAPPER.writeValue(new File(DB_PATH), taskList);
            System.out.println("Task marked to "+ status + " " + id);
        } catch (IOException e){
            System.out.print(e);
            System.err.println("Failed to update : " + id);
        }
    }
}