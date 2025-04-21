package user;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blackjack {
    private List<Card> deck;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private HBox playerCards;
    private HBox dealerCards;
    private Label playerScoreLabel;
    private Label dealerScoreLabel;
    private Label resultLabel;
    private Button hitButton;
    private Button standButton;
    private VBox root;
    private String playerName;
    private int highScore;

    public Blackjack(String playerName, int highScore) {
        this.playerName = playerName;
        this.highScore = highScore;

        // Initialize deck and hands
        deck = new ArrayList<>();
        playerHand = new ArrayList<>();
        dealerHand = new ArrayList<>();
        initializeDeck();

        // UI Components
        Label title = new Label("Blackjack - " + playerName);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

        dealerCards = new HBox(10);
        dealerScoreLabel = new Label("Dealer Score: 0");
        playerCards = new HBox(10);
        playerScoreLabel = new Label("Player Score: 0 (High Score: " + highScore + ")");
        resultLabel = new Label("");

        hitButton = new Button("Hit");
        standButton = new Button("Stand");
        Button newGameButton = new Button("New Game");

        hitButton.setDisable(true);
        standButton.setDisable(true);

        // Button actions
        hitButton.setOnAction(e -> playerHit());
        standButton.setOnAction(e -> dealerPlay());
        newGameButton.setOnAction(e -> startNewGame());

        // Layout
        HBox buttons = new HBox(10, hitButton, standButton, newGameButton);
        buttons.setAlignment(Pos.CENTER);

        root = new VBox(20, title, dealerScoreLabel, dealerCards, playerScoreLabel, playerCards, buttons, resultLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #228B22;"); // Green table color

        startNewGame();
    }

    public VBox getRoot() {
        return root;
    }

    private void initializeDeck() {
        deck.clear();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(deck);
    }

    private void startNewGame() {
        deck.clear();
        playerHand.clear();
        dealerHand.clear();
        playerCards.getChildren().clear();
        dealerCards.getChildren().clear();
        initializeDeck();

        // Deal initial cards
        playerHand.add(dealCard());
        dealerHand.add(dealCard());
        playerHand.add(dealCard());
        dealerHand.add(dealCard());

        // Show dealer's first card only
        dealerCards.getChildren().add(new ImageView(getCardImage(dealerHand.get(0))));
        dealerCards.getChildren().add(new ImageView(getCardImage("back")));

        // Show player's cards
        for (Card card : playerHand) {
            playerCards.getChildren().add(new ImageView(getCardImage(card)));
        }

        updateScores();
        resultLabel.setText("");
        hitButton.setDisable(false);
        standButton.setDisable(false);
    }

    private Card dealCard() {
        return deck.remove(0);
    }

    private void playerHit() {
        playerHand.add(dealCard());
        playerCards.getChildren().add(new ImageView(getCardImage(playerHand.get(playerHand.size() - 1))));
        updateScores();

        if (calculateScore(playerHand) > 21) {
            endGame("Player busts! Dealer wins!");
        }
    }

    private void dealerPlay() {
        hitButton.setDisable(true);
        standButton.setDisable(true);

        // Show all dealer cards
        dealerCards.getChildren().clear();
        for (Card card : dealerHand) {
            dealerCards.getChildren().add(new ImageView(getCardImage(card)));
        }

        // Dealer hits until score >= 17
        while (calculateScore(dealerHand) < 17) {
            dealerHand.add(dealCard());
            dealerCards.getChildren().add(new ImageView(getCardImage(dealerHand.get(dealerHand.size() - 1))));
        }

        updateScores();
        determineWinner();
    }

    private void updateScores() {
        playerScoreLabel.setText("Player Score: " + calculateScore(playerHand) + " (High Score: " + highScore + ")");
        dealerScoreLabel.setText("Dealer Score: " + calculateScore(dealerHand));
    }

    private int calculateScore(List<Card> hand) {
        int score = 0;
        int aces = 0;

        for (Card card : hand) {
            if (card.rank.equals("ace")) {
                aces++;
            } else if (card.rank.equals("jack") || card.rank.equals("queen") || card.rank.equals("king")) {
                score += 10;
            } else {
                score += Integer.parseInt(card.rank);
            }
        }

        for (int i = 0; i < aces; i++) {
            if (score + 11 <= 21) {
                score += 11;
            } else {
                score += 1;
            }
        }

        return score;
    }


    private void endGame(String message) {
        resultLabel.setText(message);
        hitButton.setDisable(true);
        standButton.setDisable(true);
    }

    private Image getCardImage(Card card) {
        String path = "/cards/" + card.rank + "_of_" + card.suit + ".png";
        return new Image(getClass().getResourceAsStream(path));
    }

    private Image getCardImage(String back) {
        return new Image(getClass().getResourceAsStream("/cards/back.png"));
    }

    private static class Card {
        String suit;
        String rank;

        Card(String suit, String rank) {
            this.suit = suit;
            this.rank = rank;
        }
    }
}