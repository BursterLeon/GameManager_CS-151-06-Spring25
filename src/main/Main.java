package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.LoginScreen;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginScreen loginScreen = new LoginScreen(primaryStage);

        Scene scene = new Scene(loginScreen.getVBox(), 500, 500);

        primaryStage.setTitle("GameManager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}