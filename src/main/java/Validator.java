import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Validator {
    private Scanner reader;
    private FileWriter writer;
    private ArrayList<User> users;

    public Validator() throws IOException {
        this.users = new ArrayList<>();
        this.reader = new Scanner(new File("src/main/resources/Users"));
        this.writer = new FileWriter("src/main/resources/Users", true);
    }

    public boolean isFound(String name, String password) {
        for (User user: users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void saveUser(User user) throws IOException {
        writer = new FileWriter("src/main/resources/Users");
        String[] details = user.getUserDetails().split(",");
        updateUser(details);
        for (User userObj: users) {
          writer.write(userObj.getUserDetails() + "\n");
        }
        writer.close();
    }

    private void updateUser(String[] details) {
        users.removeIf(userObj -> details[0].equals(userObj.getName()) && details[1].equals(userObj.getPassword()));
        users.add(new User(details[0], details[1], Integer.parseInt(details[2]), Boolean.parseBoolean(details[3])));
    }


    public User getUser(String name, String password) {
        for (User user: users) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void loadUsers() {
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.isEmpty() || line.isBlank()) {
                continue;
            }
            String[] splitStr = line.trim().split(",");
            users.add(new User(splitStr[0], splitStr[1], Integer.parseInt(splitStr[2]), Boolean.parseBoolean(splitStr[3])));
        }
    }

    public void registerUser(String username, String password) throws IOException {
        writer.write(username + "," + password + "," + "100" + "," + "false\n");
        users.add(new User(username, password, 100, false));
        writer.flush();
    }
}
