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

public class StartScreen {
    //creates a new UserAccount object
    //so the data from user_accounts.txt is restored and can be usesd
    //UserAccount userAccount = new UserAccount();

    private VBox vBox;
    private Label nameLabel;
    private Label passwordLabel;
    private TextField nameField;
    private TextField passwordField;
    private Button loginButton;
    private Button createAccountButton;
    private Button endButton;

    private UserAccount userAccount = new UserAccount();

    public StartScreen() {
        vBox = new VBox(20);
        nameLabel = new Label("Enter your name:");
        passwordLabel = new Label("Enter your password:");
        nameField = new TextField();
        passwordField = new TextField();

        loginButton = new Button("Login");

        createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction((ActionEvent event) -> {
            System.out.println("Create Account button clicked");
            CreateAccount createAccount = new CreateAccount(this.userAccount);
        });

        endButton = new Button("Exit");
        //writes everything from the map to the file user_accounts.txt
        //ends the program
        endButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userAccount.writeToFile();
                Platform.exit();
            }
        });

        vBox.getChildren().addAll(nameLabel,nameField,passwordLabel,passwordField,loginButton,createAccountButton,endButton);

        //FORMATTING
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
