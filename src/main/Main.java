package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label label = new Label("Hello, JavaFX!");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(label);

        Scene scene = new Scene(stackPane, 500,500);

        // Set up and display the stage
        primaryStage.setTitle("Java FX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}