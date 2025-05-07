package user;

import java.io.*;
import java.util.*;

import javafx.scene.control.Alert;
import utils.*;

//this class handles everything related to the user
public class UserAccount {
    private HashMap <String, User> userMap;

    //Maps highscores and users
    private Map <String,Integer> highscoreMap;

    //the user that is currently logged in
    private User currentLoggedInUser;
    public void setCurrentLoggedInUser(User currentLoggedInUser) {
        this.currentLoggedInUser = currentLoggedInUser;
    }

    private int currentLoggedInUserHighScore;
    public int getCurrentLoggedInUserHighScore() {
        return currentLoggedInUserHighScore;
    }
    public void setCurrentLoggedInUserHighScore(int currentLoggedInUserHighScore) {
        this.currentLoggedInUserHighScore = currentLoggedInUserHighScore;
    }

    //if a user is logged in, loggedIn is set to true
    private Boolean loggedIn = false;
    public Boolean getLoggedIn() {
        return loggedIn;
    }
    public void resetLoggedIn() {
        loggedIn = false;
    }

    public UserAccount() {
        userMap = new HashMap<>();
        highscoreMap = new HashMap<>();
        this.getUsersFromFile();
    }

    //those methods are only considered for testing
    //returns True if the user with a specific username (Key) exists in the HashMap
    public boolean userExists(String username) {
        return userMap.containsKey(username);
    }
    //ONLY FOR TESTING
    public void addUserTest (String username, String password) {
        if (!userMap.containsKey(username) && !Utility.isNullOrWhiteSpace(username) && !Utility.isNullOrWhiteSpace(password)) {
             userMap.put(username, new User(username, Utility.hashingPassword(password)));}
    }
    //ONLY FOR TESTING
    public User getUser(String username) {
        return userMap.get(username);
    }
    //ONLY FOR TESTING
     public Boolean testLoginValidation (String username, String password) {
        if (!this.userMap.containsKey(username)) {
            return false;
        }
        else {
            //returns the user that is saved in the HashMap with the given username and assigns it to user
            User user = this.userMap.get(username);
            if (!user.getPassword().equals(Utility.hashingPassword(password))) {
                return false;
            }
            else {
                if (user.getPassword().equals(Utility.hashingPassword(password))) {
                    this.setCurrentLoggedInUser(user);
                    setCurrentLoggedInUserHighScore(user.getHighScore());
                    loggedIn = true;
                    return true;
                }
            }
        }
        return false;
    }



    public void newUser (String username, String password) {
        if (!userMap.containsKey(username) && !Utility.isNullOrWhiteSpace(username) && !Utility.isNullOrWhiteSpace(password)) {
             userMap.put(username, new User(username, Utility.hashingPassword(password)));

             //message, that the user has been added
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("User successfully created!");
            alert.showAndWait();
        }
        else if (Utility.isNullOrWhiteSpace(username) && Utility.isNullOrWhiteSpace(password)) {
                errorWindow("Username and password is required!");
            }
        else if (Utility.isNullOrWhiteSpace(username)) {
                errorWindow("Username is required!");
            }
        else if (userMap.containsKey(username)) {
                errorWindow("Username already exists!");
            }
        else if (Utility.isNullOrWhiteSpace(password)) {
                errorWindow("Password is required!");
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
        try (
                BufferedReader reader = new BufferedReader(new FileReader("src/user/user_accounts.txt"));
                BufferedReader readerHighScore = new BufferedReader(new FileReader("src/user/high_scores.txt"));
        )
        {
            String line;
            line = reader.readLine();
            String lineHighScore = readerHighScore.readLine();
            while(line != null && lineHighScore != null) {
                //lines in file are comma seperated values name, password, highScore
                String[] lines = line.split(",");
                String name = lines [0];
                String password = lines [1];

                String[] highScores = lineHighScore.split(",");
                int highScore = Integer.parseInt(highScores[1]);

                //adds the name as the key and the user object as a value to the userMap
                userMap.put(name, new User(name, password, highScore));
                highscoreMap.put(name,highScore);

                //read new line from the file
                line = reader.readLine();
                lineHighScore = readerHighScore.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //FILE WRITER
    //writes everything from the map to the file user_accounts.txt
    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/user/user_accounts.txt")))
        {
            //a collection view of the values contained in this map
            for (User user : userMap.values()) {
                String line = user.getName() + "," + user.getPassword();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writeToHighScoreFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/user/high_scores.txt")))
        {
            //a collection view of the values contained in this map
            for (User user : userMap.values()) {
                String line = user.getName() + "," + user.getHighScore();
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
            //returns the user that is saved in the HashMap with the given username and assigns it to user
            User user = this.userMap.get(username);
            if (!user.getPassword().equals(Utility.hashingPassword(password))) {
                //message, that the user has not been added
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Wrong password!");
            alert.showAndWait();
            }
            else {
                if (user.getPassword().equals(Utility.hashingPassword(password))) {
                    this.setCurrentLoggedInUser(user);
                    setCurrentLoggedInUserHighScore(user.getHighScore());
                    loggedIn = true;
                    System.out.println("User logged in: " + user.toString());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("User successfully logged in!");
                    alert.showAndWait();
                }
            }
        }
    }
    public void changeUserHighScore (int newHighScore) {
        userMap.get(currentLoggedInUser.getName()).setHighscore(newHighScore);
        highscoreMap.put(currentLoggedInUser.getName(),newHighScore);
    }

    public ArrayList<String> getTopHighScore () {
        ArrayList<String> highScores = new ArrayList<>();

        //to convert Map into List with entries (String and Integer)
        List<Map.Entry<String, Integer>> scoreList = new ArrayList<>(highscoreMap.entrySet());
        //sort the List from highest to lowest
        scoreList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        //save the top 5 (or less if the list has a size of smaller 5) entries in a new List
        List<Map.Entry<String, Integer>> topScoreList = scoreList.subList(0, Math.min(5, scoreList.size()));

        for (Map.Entry<String, Integer> entry : topScoreList) {
            highScores.add(entry.getKey() + ":" + entry.getValue());
        }
        return highScores;
    }

    private void errorWindow(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}
