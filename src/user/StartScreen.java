package user;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import window.CreateAccountWindow;
import window.GameWindow;
import window.WindowManager;

//the first window that opens which lets the user log in
public class StartScreen {
    //creates a new UserAccount object
    //so the data from user_accounts.txt is restored and can be usesd

    private VBox vBox;
    private Label nameLabel;
    private Label passwordLabel;
    private TextField nameField;
    private TextField passwordField;
    private Button loginButton;
    private Button createAccountButton;
    private Button endButton;

    private UserAccount userAccount;

    public StartScreen(UserAccount userAccount) {
        vBox = new VBox(20);
        nameLabel = new Label("Enter your name:");
        passwordLabel = new Label("Enter your password:");
        nameField = new TextField();
        passwordField = new TextField();

        this.userAccount = userAccount;

        //LOGIN
        loginButton = new Button("Login");
        loginButton.setOnAction((ActionEvent event) -> {
            userAccount.loginValidation(nameField.getText(), passwordField.getText());
            //resets the 2 textfields
            nameField.clear();
            passwordField.clear();
            if (userAccount.getLoggedIn())
                WindowManager.openWindow(new GameWindow(userAccount),"GameWindow");
        });

        //CREATE ACCOUNT
        createAccountButton = new Button("Create Account");
        createAccountButton.setOnAction((ActionEvent event) -> {
            System.out.println("Create Account button clicked");
            nameField.clear();
            passwordField.clear();
//            CreateAccountWindow createAccountWindow = new CreateAccountWindow(userAccount);
            //deleted the line to avoid creating more and more objects of CreateAccountWindow
            WindowManager.openWindow(new CreateAccountWindow(userAccount), "CreateAccountWindow");
        });

        endButton = new Button("Exit");
        //writes everything from the map to the file user_accounts.txt
        //ends the program
        endButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userAccount.writeToFile();
                userAccount.writeToHighScoreFile();
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
    }}


