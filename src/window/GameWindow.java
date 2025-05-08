package window;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.UserAccount;

import java.util.HashMap;
import java.util.Map;

//window that lets the user choose between different games
public class GameWindow extends GameManagerWindow implements hasHBox{
    private HBox hBox;
    private VBox highscoreBox;

    private Button snakeButton;
    private Button blackJackButton;
    private Button logOutButton;

    private Button futureGameButton1;
    private Button futureGameButton2;

    private Label scoreLabel;

    public GameWindow(UserAccount userAccount) {
        super(userAccount);

        hBox = new HBox(100);
        highscoreBox = new VBox(20);

        scoreLabel = new Label("Top High Score:");
        highscoreBox.getChildren().add(scoreLabel);

        for (String el: super.getUserAccount().getTopHighScore()) {
            highscoreBox.getChildren().add(new Label(el));
        }

        snakeButton = new Button("snake");
        snakeButton.setOnAction(e -> {
            new snake.SnakeGameFX(userAccount).start(new Stage());
        });

        blackJackButton = new Button("Black Jack");

        logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> {
            super.getUserAccount().resetLoggedIn();
            WindowManager.closeWindow(this);
        });

        futureGameButton1 = new Button("Future Game");
        futureGameButton1.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Future Game");
                    alert.setHeaderText(null);
                    alert.setContentText("Here will soon be a future game.");
                    alert.showAndWait();
        });
        futureGameButton2 = new Button("Future Game2");
        futureGameButton2.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Future Game");
            alert.setHeaderText(null);
            alert.setContentText("Here will soon be a future game.");
            alert.showAndWait();
        });

        super.getVBox().getChildren().addAll(snakeButton, blackJackButton, futureGameButton1, futureGameButton2, logOutButton);
        for (Node node : super.getVBox().getChildren()) {
            VBox.setMargin(node, new Insets(5));
        }
        for (Node node : highscoreBox.getChildren()) {
            VBox.setMargin(node, new Insets(5));
        }

        hBox.getChildren().addAll(super.getVBox(),highscoreBox);
    }
    public HBox getHBox() {
        return hBox;
    }
    public VBox getHighscoreBox() {
        return highscoreBox;
    }
}
