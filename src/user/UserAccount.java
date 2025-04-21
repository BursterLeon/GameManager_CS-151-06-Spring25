package user;

import java.io.*;
import java.util.*;

import javafx.scene.control.Alert;
import utils.*;

public class UserAccount {
    private HashMap <String, User> userMap;

    public UserAccount() {
        userMap = new HashMap<>();
        this.getUsersFromFile();
    }

    public void newUser (String username, String password) {
        if (!userMap.containsKey(username) && !utility.Utility.isNullOrWhiteSpace(username) && !utility.Utility.isNullOrWhiteSpace(password)) {
             userMap.put(username, new User(username, password));

             //message, that the user has been added
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User successfully created!");
            alert.showAndWait();
        }
        if (utility.Utility.isNullOrWhiteSpace(username) && utility.Utility.isNullOrWhiteSpace(password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username and password is required!");
            }
        if (utility.Utility.isNullOrWhiteSpace(username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username is required!");
            }
        if (utility.Utility.isNullOrWhiteSpace(password)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password is required!");
            }
        if (userMap.containsKey(username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists!");
                alert.showAndWait();
            }
//                //message, that the user has not been added
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error");
//                alert.setHeaderText(null);
//                alert.setContentText("User not created!");
//                alert.showAndWait();
    }

    //reads out the user_accounts.txt file
    //adds every key (name) and value (user) to the HashMap
    // (BufferedReader is closed automatically at the end)
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
                int highScore = Integer.parseInt(lines[2]);

                //adds the name as the key and the user object as a value to the userMap
                userMap.put(name, new User(name, password, highScore));

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

    public void loginValidation (String username, String password) {
        if (!this.userMap.containsKey(username)) {
            //message, that the user has not been added
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("User doesn't exist!");
            alert.showAndWait();
        }
        else {
            User user = this.userMap.get(username);
            if (!user.getPassword().equals(password)) {
                //message, that the user has not been added
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong password!");
            alert.showAndWait();
            }
            else {
                if (user.getPassword().equals(password)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("User successfully logged in!");
                    alert.showAndWait();
                }
            }
        }
    }
}
