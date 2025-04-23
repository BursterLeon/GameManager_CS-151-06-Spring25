package user;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import window.GameManagerWindow;
import window.WindowManager;

public class CreateAccountWindow extends GameManagerWindow {
    private Label nameLabel;
    private Label passwordLabel;
    private TextField nameField;
    private TextField passwordField;
    private Button createAccountButton;
    private Button returnButton;

//    private Stage stage;

    public CreateAccountWindow(UserAccount userAccount) {
        super(userAccount);

        nameLabel = new Label("Enter your name:");
        passwordLabel = new Label("Enter your password:");
        nameField = new TextField();
        passwordField = new TextField();

        createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction (e -> {
                String name = nameField.getText();
                String password = passwordField.getText();
                //int highScore = utility.Utility.isValidInt(highScoreField.getText())?Integer.parseInt(highScoreField.getText()):0;
                userAccount.newUser(name,password);
                nameField.clear();
                passwordField.clear();
                WindowManager.closeWindow(this);
        });

        returnButton = new Button("Return");
        returnButton.setOnAction (e -> {
            WindowManager.closeWindow(this);
        });

        super.getVBox().getChildren().addAll(nameLabel,nameField,passwordLabel,passwordField,createAccountButton,returnButton);

        for (Node node : super.getVBox().getChildren()) {
            VBox.setMargin(node, new Insets(5));
        }
    }
}
