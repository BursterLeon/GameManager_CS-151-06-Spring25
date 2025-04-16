package user;

import java.io.*;
import java.util.*;

import utils.*;

public class UserAccount {
    private Map <String, User> userMap;

    public UserAccount() {
        userMap = new HashMap<>();
        getUsersFromFile();
    }

    public void newUser (String username, String password, int highScore) {
        if (!userMap.containsKey(username) && !utility.Utility.isNullOrWhiteSpace(username)) {
             userMap.put(username, new User(username, password, highScore));
        }
    }

    //reads out the user_accounts.txt file (BufferedReader is closed automatically at the end)
    public void getUsersFromFile () {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/user/user_accounts.txt")) )
        {
            String line;
            line = reader.readLine();
            while(line != null) {
                //lines in file are comma seperated values name, password, highScore
                String[] lines = line.split(",");
                String name = lines [0];
                String password = lines [1];
                int score = Integer.parseInt(lines[2]);

                //adds the name as the key and the user object as a value to the userMap
                userMap.put(name, new User(name, password, score));

                //read new line from the file
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //writes everything from the map to the file user_accounts.txt
    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/user/user_accounts.txt")))
        {
            //a collection view of the values contained in this map
            for (User user : userMap.values()) {
                String line = user.getName() + "," + user.getPassword() + "," + user.getHighScore();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
