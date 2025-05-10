package blackjack;


import blackjack.BlackjackMain.Turn;
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


    private HBox playerHand;
    private HBox dealerHand;  // box to display dealer's cards
    private Button hitButton;  // hit button for player's turn
    private Button standButton;  // stand button for player's turn
    private Button startButton;  // start button to begin the game
    private Label gameStatusLabel;  // display the status of the game
    private VBox root;  //
    private HBox botHand1;
    private HBox botHand2; // Display the second hand
    private Label playerScoreLabel;
    @Override
    public void start(Stage primaryStage) {
        // Initialize the Blackjack game
        game = new BlackjackMain();
        gameStatusLabel = new Label("Welcome to Blackjack! Press 'Start' to begin.");
        playerScoreLabel = new Label("Player Score: 0");










        // create HBoxes


        playerHand = new HBox(5);
        botHand1 =new HBox(5);
        botHand2 =new HBox(5);
        dealerHand = new HBox(5);


        Label playerLabel = new Label("Player Hand");
        Label botHand1Label = new Label("Bot 1 Hand");
        Label botHand2Label = new Label("Bot 2 Hand");
        Label dealerLabel = new Label("Dealer Hand");


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




        //Setup Star start action
        standButton.setOnAction(event -> stand());






// Root layout
        root = new VBox(10);


        root.getChildren().addAll(
                startButton,
                gameStatusLabel,
                playerLabel, playerHand,
                botHand1Label,botHand1,
                botHand2Label,botHand2,
                dealerLabel, dealerHand,
                hitButton, standButton,
                playerScoreLabel
        );


        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);




        //Out side setuo
        Scene scene = new Scene(root, 800, 600);  // Increse scen size to 4 hand in to display in 1 screen
        primaryStage.setTitle("Blackjack Game");
        primaryStage.setScene(scene);
        primaryStage.show();








    }




    // Method to start the game
    private void startGame() {
        game.resetGame();  // Initialize game
        // Update the UI with initial hand displays




        hitButton.setDisable(false);  // Enable buttons after game start
        standButton.setDisable(false);
        gameStatusLabel.setText("Your turn! Press 'Hit' or 'Stand'.");
        playerScoreLabel.setText("Player Score: 0");


        // Clear the hand display so when let say . press again it will restart
        playerHand.getChildren().clear();
        botHand1.getChildren().clear();
        botHand2.getChildren().clear();
        dealerHand.getChildren().clear();


        updateUI(); // update initial
    }




    // feature hit
    private void hit() {
        //game.switchTurn();




        game.playTurn();  // Player takes a hit
        updateUI();


// Make after it bust get all player hit
        if (game.getPlayer().bust()) {
            gameStatusLabel.setText("You busted! Dealer wins.");
            endGame();
        }
    }




    // Feature stand
    private void stand() {
        updateUI();  // Update the UI with final hands
        gameStatusLabel.setText("Player Stand, Bot 1 turn");


        game.switchTurn(); // Go to bot1
        game.playTurn();
        updateUI();
        gameStatusLabel.setText("Bot 1 done. bot2 turn");
        game.switchTurn(); // Go to bot2
        game.playTurn();
        updateUI();
        gameStatusLabel.setText("Bot 2 done. Dealer turn");


        game.switchTurn(); // Go to Dealer
        // Now dealer logic when dealer lesss then 15 it a logic to let them hit
        while (game.getDealer().getPlayerHandTotal() < 15)  {
            game.playTurn();  // Dealer hits if total < 15
            updateUI();
            gameStatusLabel.setText("Dealer hits. Dealer's total: " + game.getDealer().getPlayerHandTotal());
        }
        gameStatusLabel.setText("Dealer stands. Checking results...");
        updateUI();
        String result = game.checkPlayerHand();
        gameStatusLabel.setText(result);
        endGame();


    }




    //Feature Uodate UI
    private void updateUI() {
        playerHand.getChildren().clear();  // Clear prevous
        botHand1.getChildren().clear();
        botHand2.getChildren().clear();
        dealerHand.getChildren().clear();


        // It will update the UI card base on what the logic and plater pakying of each hand
        for (Card card : game.getPlayer().getPlayerHand()) {
            String imagePath = getCardImagePath(card);
            Image cardImage = new Image(imagePath); // Select in the file of the image
            ImageView cardView = new ImageView(cardImage);// Now view it
            cardView.setFitWidth(100); //Resize the cars becasur file of the image was too large
            cardView.setFitHeight(150);
            cardView.setPreserveRatio(true);
            playerHand.getChildren().add(cardView);
        }
        playerScoreLabel.setText("Player Score: " + game.getPlayer().getPlayerHandTotal());


        for (Card card : game.getBotPlayer1().getPlayerHand()) {
            String imagePath = getCardImagePath(card);
            Image cardImage = new Image(imagePath);
            ImageView cardView = new ImageView(cardImage);
            cardView.setFitWidth(100);
            cardView.setFitHeight(150);
            cardView.setPreserveRatio(true);
            botHand1.getChildren().add(cardView);
        }


        for (Card card : game.getBotPlayer2().getPlayerHand()) {
            String imagePath = getCardImagePath(card);
            Image cardImage = new Image(imagePath);
            ImageView cardView = new ImageView(cardImage);
            cardView.setFitWidth(100);
            cardView.setFitHeight(150);
            cardView.setPreserveRatio(true);
            botHand2.getChildren().add(cardView);
        }
        for (Card card : game.getDealer().getPlayerHand()) {
            String imagePath = getCardImagePath(card);
            Image cardImage = new Image(imagePath);
            ImageView cardView = new ImageView(cardImage);
            cardView.setFitWidth(100);
            cardView.setFitHeight(150);
            cardView.setPreserveRatio(true);
            dealerHand.getChildren().add(cardView);
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




    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
