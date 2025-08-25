import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasksToHardDisk(TaskList tasks) {
        try {
            ArrayList<Task> listStrings = tasks.getTasks();
            File taskFile = new File(filePath);
            taskFile.getParentFile().mkdirs(); // Create directories if they don't exist
            FileOutputStream fileOut = new FileOutputStream(taskFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(listStrings);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to hard disk: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Task> loadTasksFromHardDisk() throws BruhException {
        File taskFile = new File(filePath);
        if (!taskFile.exists()) {
            return new ArrayList<>(); // No tasks to load
        }
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<Task> listStrings = (ArrayList<Task>) in.readObject();
            in.close();
            fileIn.close();
            return listStrings;
        } catch (IOException | ClassNotFoundException e) {
            throw new BruhException("Error loading tasks from hard disk: " + e.getMessage());
        }
    }
}
