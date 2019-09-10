import java.io.*;

/**
 * Represents a storage object that will read and write files into specific file path.
 */
public class Storage {
    private TaskList taskList;
    private File taskListText;

    public Storage(TaskList taskList){
        this.taskList = taskList;
    }

    public void loadFile() throws IOException {
        try {
            checkFile();
            taskListText = new File("TaskList.txt");
            BufferedReader TasksFile = new BufferedReader(new FileReader(taskListText));
            String dataLine = "";
            while ((dataLine = TasksFile.readLine()) != null) {
                String[] data = dataLine.split(" \\| ");
                switch (data[0]) {
                case ("T"):
                    Task todoFile = new Todo(data[2]);
                    taskList.add(todoFile);
                    if (data[1].equals("1")) {
                        todoFile.markAsDone();
                    }
                    break;

                case ("E"):
                    Task eventFile = new Event(data[2], data[3]);
                    taskList.add(eventFile);
                    if (data[1].equals("1")) {
                        eventFile.markAsDone();
                    }
                    break;

                case ("D"):
                    Task deadlineFile = new Deadline(data[2], data[3]);
                    taskList.add(deadlineFile);
                    if (data[1].equals("1")) {
                        deadlineFile.markAsDone();
                    }
                    break;

                default:
                    System.out.println("error");
                }
            }
        } catch (FileNotFoundException e) {
        }
    }

    public void rewriteData() throws IOException {
        try (PrintStream out = new PrintStream(new FileOutputStream(taskListText))) {
            for (int i = 0; i < taskList.size(); i++) {
                Task t = taskList.get(i);
                if (t.getType().equals("todo")) {
                    out.print(t.getSymbol() + " | " + t.getBoolean() + " | " + t.getDescription() + "\n");
                } else {
                    out.print(t.getSymbol() + " | " + t.getBoolean() + " | " + t.getDescription() + " | " + t.getDateString() + "\n");
                }
            }
        }
    }

    public void checkFile() throws IOException {
        File tmpDir = new File("TaskList.txt");
        if (!tmpDir.exists()) {
            tmpDir.createNewFile();
        }
    }
}