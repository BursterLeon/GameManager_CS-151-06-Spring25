package blackjack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class MainApp extends Application {

    private BlackjackMain game;
    private HBox playerHandBox;
    private HBox dealerHandBox;  // Box to display dealer's cards
    private Button hitButton;  // Hit button for player's turn
    private Button standButton;  // Stand button for player's turn
    private Button startButton;  // Start button to begin the game
    private Label gameStatusLabel;  // Display the status of the game
    private VBox root;  //

    @Override
    public void start(Stage primaryStage) {
        // Initialize the Blackjack game
        game = new BlackjackMain();
        gameStatusLabel = new Label("Welcome to Blackjack! Press 'Start' to begin.");


        // Create HBoxes
        playerHandBox = new HBox(10);  // For player's cards
        dealerHandBox = new HBox(10);  // For dealer's cards

        // Create the buttons
        hitButton = new Button("Hit");
        standButton = new Button("Stand");
        startButton = new Button("Start Game");

       // enabke
        hitButton.setDisable(true);
        standButton.setDisable(true);


        startButton.setOnAction(event -> startGame());

        // Set up hir
        hitButton.setOnAction(event -> hit());

        //n
        standButton.setOnAction(event -> stand());


        root = new VBox(20);
        root.getChildren().addAll(startButton, gameStatusLabel, playerHandBox, dealerHandBox, hitButton, standButton);


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

    // feature hit
    private void hit() {
        game.playTurn();  // Player takes a hit
        updateUI();

        if (game.getPlayer().bust()) {
            gameStatusLabel.setText("You busted! Dealer wins.");
            hitButton.setDisable(true);
            standButton.setDisable(true);
        }
    }

    // Feature stand
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

    //Feature Uodate UI
    private void updateUI() {
        playerHandBox.getChildren().clear();  // Clear previous hand display
        dealerHandBox.getChildren().clear();  // Clear previous hand display
        for (Card card : game.getPlayer().getPlayerHand()) {
            String imagePath = getCardImagePath(card); // Get card image to display
            Image cardImage = new Image(imagePath);
            playerHandBox.getChildren().add(new ImageView(cardImage));
        }
        //int playerScoreGet = game.getPlayer().getPlayerHandTotal();
        //playerScoreLabel.setText("Player Score: " + playerScoreLabel);


        dealerHandBox.getChildren().clear();
        for (Card card : game.getDealer().getPlayerHand()) {
            String imagePath = getCardImagePath(card);
            Image cardImage = new Image(imagePath);
            dealerHandBox.getChildren().add(new ImageView(cardImage));
        }
        //int dealerScoreGet = game.getDealer().getPlayerHandTotal();
        //dealerScoreLabel.setText("Dealer Score: " + dealerScoreLabel);
        // PlayerScoreLabel.setText("Player Score: " + player.getScore());
        //dealerScoreLabel.setText("Dealer Score: " + dealer.getScore());
        // Display the player's hand.
        //for (Card card : game.getPlayer().getPlayerHand()) {
        //            playerHandBox.getChildren().add(new Label(card.toString()));  // Player's cards
        //        }
        //
        //        // Display the dealer's hand (dealer's cards are revealed after player's turn)
        //        if (game.getDealer().getPlayerHand().size() > 1) {
        //            for (Card card : game.getDealer().getPlayerHand()) {
        //                dealerHandBox.getChildren().add(new Label(card.toString()));  // Dealer's cards
        //            }
        //        } else {
        //            dealerHandBox.getChildren().add(new Label("Hidden Card"));  // Dealer's second card is hidden initially
        //        }
        // **//

    }


    //DIsplay image
        //playerScoreLabel.setText("Player Score: " + player.getScore());
        //dealerScoreLabel.setText("Dealer Score: " + dealer.getScore());




//GET THE CARD IMAGE FROM FILE
private String getCardImagePath(Card card) {
    String fileName = card.toString()+".png";// Call it cause structure the resoure filw name base on Sui + rank to make it match and call
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


}
