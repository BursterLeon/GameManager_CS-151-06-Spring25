package window;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.UserAccount;

public class GameWindow extends GameManagerWindow {
    private Button snakeButton;
    private Button blackJackButton;
    private Button logOutButton;

    public GameWindow(UserAccount userAccount) {
        super(userAccount);

        snakeButton = new Button("snake");
        snakeButton.setOnAction(e -> {
            new snake.SnakeGameFX().start(new Stage());
        });

        blackJackButton = new Button("Black Jack");

        logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> {
            super.getUserAccount().resetLoggedIn();
            WindowManager.closeWindow(this);
        });

        super.getVBox().getChildren().addAll(snakeButton, blackJackButton, logOutButton);
        for (Node node : super.getVBox().getChildren()) {
            VBox.setMargin(node, new Insets(5));
        }
    }
}
