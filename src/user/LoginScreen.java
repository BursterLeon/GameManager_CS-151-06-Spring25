package user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginScreen {
    private VBox vBox;
    private Label nameLabel;
    private Label passwordLabel;
    private Label highScoreLabel;
    private TextField nameField;
    private TextField passwordField;
    private TextField highScoreField;
    private Button loginButton;

    public LoginScreen() {
        vBox = new VBox(20);
        nameLabel = new Label("Enter your name:");
        passwordLabel = new Label("Enter your password:");
        highScoreLabel = new Label("High score:");
        nameField = new TextField();
        passwordField = new TextField();
        highScoreField = new TextField();
        loginButton = new Button("Login");

        vBox.getChildren().addAll(nameLabel,nameField,passwordLabel,passwordField,highScoreLabel,highScoreField,loginButton);

        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(10);

        for (Node node : vBox.getChildren()) {
            VBox.setMargin(node, new Insets(5));
        }
    }
    public VBox getVBox() {
        return vBox;
    }
}
