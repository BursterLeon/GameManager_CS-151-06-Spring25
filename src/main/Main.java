package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import user.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        UserAccount userAccount = new UserAccount();
        StartScreen startScreen = new StartScreen(userAccount);

        Scene scene = new Scene(startScreen.getVBox(), 500,500);

        // Set up and display the stage
        primaryStage.setTitle("GameManager");
        primaryStage.setScene(scene);

        //when the user closes the program, User.Account.writeToFile() is called
        //making sure that all the information is saved to user_accounts.txt
        primaryStage.setOnCloseRequest(event -> {
            userAccount.writeToFile();
            Platform.exit();
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}