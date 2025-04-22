package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeGame extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/snake/snakegame.fxml"));
        Parent root = loader.load();
        SnakeController controller = loader.getController();

        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/main/snake/snake.css").toExternalForm());

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}