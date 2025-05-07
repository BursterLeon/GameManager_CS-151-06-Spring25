package blackjack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private BlackjackMain game;  // Instance of Blackjack game logic
    private HBox playerHandBox;  // Box to display player's cards
    private HBox dealerHandBox;  // Box to display dealer's cards
    private Button hitButton;  // Hit button for player's turn
    private Button standButton;  // Stand button for player's turn
    private Button startButton;  // Start button to begin the game
    private Label gameStatusLabel;  // Display the status of the game
    private VBox root;  // The root layout containing all UI elements

    @Override
    public void start(Stage primaryStage) {
        // Initialize the Blackjack game
        game = new BlackjackMain();
        gameStatusLabel = new Label("Welcome to Blackjack! Press 'Start' to begin.");

        // Create HBoxes to display cards
        playerHandBox = new HBox(10);  // For player's cards
        dealerHandBox = new HBox(10);  // For dealer's cards

        // Create the buttons for player actions
        hitButton = new Button("Hit");
        standButton = new Button("Stand");
        startButton = new Button("Start Game");

        // Initially, disable the Hit and Stand buttons
        hitButton.setDisable(true);
        standButton.setDisable(true);

        // Set up action for 'Start Game' button
        startButton.setOnAction(event -> startGame());

        // Set up action for 'Hit' button
        hitButton.setOnAction(event -> hit());

        // Set up action for 'Stand' button
        standButton.setOnAction(event -> stand());

        // Create a layout (VBox)
        root = new VBox(20);
        root.getChildren().addAll(startButton, gameStatusLabel, playerHandBox, dealerHandBox, hitButton, standButton);

        // Set the padding and spacing for the layout
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        // Scene setup
        Scene scene = new Scene(root, 400, 300);  // Set scene size
        primaryStage.setTitle("Blackjack Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to start the game
    private void startGame() {
        game.startGame();  // Initialize game
        updateUI();  // Update the UI with initial hand displays

        hitButton.setDisable(false);  // Enable buttons after game start
        standButton.setDisable(false);
        gameStatusLabel.setText("Your turn! Press 'Hit' or 'Stand'.");
    }

    // Method to handle the 'Hit' action
    private void hit() {
        game.playTurn();  // Player takes a hit
        updateUI();  // Update the UI after the hit

        if (game.getPlayer().bust()) {
            gameStatusLabel.setText("You busted! Dealer wins.");
            hitButton.setDisable(true);
            standButton.setDisable(true);
        }
    }

    // Method to handle the 'Stand' action
    private void stand() {
        game.playTurn();  // Player stands
        game.checkPlayerHand();  // Check player hand
        updateUI();  // Update the UI with final hands

        if (!game.getPlayer().bust()) {
            game.checkDealerHand();  // Check dealer hand after player stands
        }
        hitButton.setDisable(true);  // Disable buttons after stand
        standButton.setDisable(true);
    }

    // Method to update the UI with the current state of the game
    private void updateUI() {
        playerHandBox.getChildren().clear();  // Clear previous hand display
        dealerHandBox.getChildren().clear();  // Clear previous hand display

        // Display the player's hand
        for (Card card : game.getPlayer().getPlayerHand()) {
            playerHandBox.getChildren().add(new Label(card.toString()));  // Player's cards
        }

        // Display the dealer's hand (dealer's cards are revealed after player's turn)
        if (game.getDealer().getPlayerHand().size() > 1) {
            for (Card card : game.getDealer().getPlayerHand()) {
                dealerHandBox.getChildren().add(new Label(card.toString()));  // Dealer's cards
            }
        } else {
            dealerHandBox.getChildren().add(new Label("Hidden Card"));  // Dealer's second card is hidden initially
        }
    }
    private String getCardImagePath(Card card) {
        String rank = card.getRank().toUpperCase();
        String suit = card.getSuit().toLowerCase();
        String fileName = suit + "_" + rank+ ".png";// Call it cause structure the resoure filw name base on Sui + rank to make it match and call

        return "resource/" + fileName;  }

    // End game
    private void endGame() {
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }

    // Getter for the VBox layout (required for Scene setup)
    public VBox getVBox() {
        return root;  // Return the root VBox containing the game UI
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
