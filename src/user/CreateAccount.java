package user;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import utils.*;

public class CreateAccount {
    private VBox vBox;
    private Label nameLabel;
    private Label passwordLabel;
    private TextField nameField;
    private TextField passwordField;
    private Button createAccountButton;
    private Button endButton;

    private UserAccount userAccount;

    private Stage stage;

    public CreateAccount(UserAccount userAccount) {
        this.userAccount = userAccount;

        stage = new Stage();
        stage.setTitle("Create Account");

        vBox = new VBox(20);
        nameLabel = new Label("Enter your name:");
        passwordLabel = new Label("Enter your password:");
        nameField = new TextField();
        passwordField = new TextField();

        createAccountButton = new Button("Login");
        createAccountButton.setOnAction (new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = nameField.getText();
                String password = passwordField.getText();
                //int highScore = utility.Utility.isValidInt(highScoreField.getText())?Integer.parseInt(highScoreField.getText()):0;
                userAccount.newUser(name,password);
                nameField.clear();
                passwordField.clear();
                stage.close();
            }
        });


        vBox.getChildren().addAll(nameLabel,nameField,passwordLabel,passwordField,createAccountButton);

        //FORMATTING
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.TOP_LEFT);
        vBox.setSpacing(10);

        for (Node node : vBox.getChildren()) {
            VBox.setMargin(node, new Insets(5));
        }

        Scene scene = new Scene(this.getVBox(), 500,500);
        stage.setScene(scene);
        stage.show();
    }
    public VBox getVBox() {
        return vBox;
    }
}
