package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import user.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginScreen loginScreen = new LoginScreen();

        Scene scene = new Scene(loginScreen.getVBox(), 500,500);

        // Set up and display the stage
        primaryStage.setTitle("GameManager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}