package window;

import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Utility;

//class for setting up new windows
public class WindowManager {
    private WindowManager() {
    }

    public static void openWindow(GameManagerWindow gameManagerWindow,String title) {
        if (gameManagerWindow != null && !Utility.isNullOrWhiteSpace(title)) {
            gameManagerWindow.getStage().setTitle(title);

            Scene scene = new Scene(gameManagerWindow.getVBox(), 500, 500);
            gameManagerWindow.getStage().setScene(scene);
            gameManagerWindow.getStage().show();
        }
    }
    public static void closeWindow(GameManagerWindow gameManagerWindow) {
        if (gameManagerWindow != null) {
            gameManagerWindow.getStage().close();
        }
    }
}
