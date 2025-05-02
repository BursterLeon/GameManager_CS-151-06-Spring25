package window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.UserAccount;

// superclass for all windows in the game manager, except for the games
public class GameManagerWindow {
    private VBox vBox;
    private UserAccount userAccount;
    private Stage stage;

    public GameManagerWindow(UserAccount userAccount) {
        this.userAccount = userAccount;
        vBox = new VBox(20);
        stage = new Stage();
        //FORMATTING
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(10);
    }
    public VBox getVBox() {
        return vBox;
    }
    public Stage getStage() {
        return stage;
    }
    public UserAccount getUserAccount() {
        return userAccount;
    }
}
