package blackjack;
import blackjack.Dealer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import blackjack.BlackjackMain;
import blackjack.Human;
import blackjack.Dealer;
import java.util.ArrayList;
public class BlackjackMain {

    private Deck deck;
    private Human player;
    private Dealer dealer;
    private ComputerPlayer computerPlayer1;
    private ComputerPlayer computerPlayer2;

    private Turn currentTurn;

    public enum Turn {
        HUMAN, BOT1, BOT2, DEALER;
    }
    public Human getPlayer() {
        return player;
    }


    //constructor
    public BlackjackMain() {
        this.deck = new Deck();
        this.player = new Human(1000, 0, new ArrayList<>());
        this.dealer = new Dealer(new ArrayList<>());
        this.computerPlayer1 = new ComputerPlayer(new ArrayList<>());
        this.computerPlayer2 = new ComputerPlayer(new ArrayList<>());
        this.currentTurn = Turn.HUMAN;
        deck.shuffle();
    }



    // All players will play their hand once it's their turn
    public void playTurn() {
        switch(currentTurn) {
            case HUMAN:
                player.playTurn(deck);
                break;
            case BOT1:
                computerPlayer1.playTurn1(deck);
                break;
            case BOT2:
                computerPlayer2.playTurn2(deck);
                break;
            case DEALER:
                dealer.playTurn(deck);
                break;
        }
    }

    // Turns rotate starting from Human to Dealer and then goes back to Human for the next round
    public void switchTurn() {
        switch(currentTurn) {
            case HUMAN:
                currentTurn = Turn.BOT1;
                break;
            case BOT1:
                currentTurn = Turn.BOT2;
                break;
            case BOT2:
                currentTurn = Turn.DEALER;
                break;
            case DEALER:
                currentTurn = Turn.HUMAN;
                break;
        }
    }

    public void checkPlayerHand() {
        int total = player.getPlayerHandTotal();


        if(player.getPlayerHandTotal() == 21) {
            System.out.println("Blackjack! You win!");
        } else if(player.bust()) {
            System.out.println("You busted with " + player.getPlayerHandTotal());
            System.out.println("You lose!");
        } else if(dealer.bust()) {
            System.out.println("Dealer busts with " + dealer.getPlayerHandTotal());
            System.out.println("You win!");
        } else if(player.getPlayerHandTotal() == dealer.getPlayerHandTotal()) {
            System.out.println("Push! It's a draw!");
        } else if(player.getPlayerHandTotal() > dealer.getPlayerHandTotal()) {
            System.out.println("You have " + player.getPlayerHandTotal());
            System.out.println("Dealer has " + dealer.getPlayerHandTotal());
            System.out.println("You win!");
        } else if(player.getPlayerHandTotal() < dealer.getPlayerHandTotal()) {
            System.out.println("You have " + player.getPlayerHandTotal());
            System.out.println("Dealer has " + dealer.getPlayerHandTotal());
            System.out.println("You lose!");
        }
    }

    public void checkDealerHand() {
        if(dealer.bust()) {
            System.out.println("Dealer busted with " + dealer.getPlayerHandTotal());
        }
    }

    public void checkComputerHand() {
        deck.shuffle();
        player = new Human(1000, 0, new ArrayList<>());
        dealer = new Dealer(new ArrayList<>());
        computerPlayer1 = new ComputerPlayer(new ArrayList<>());
        computerPlayer2 = new ComputerPlayer(new ArrayList<>());
        dealInitialCards();
    }

    // Give 2 card for the player
    public void dealInitialCards() {
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.hit()); // Loop 2 time to give 2 card for each plater
            dealer.addCard(deck.hit());
            computerPlayer1.addCard(deck.hit());
            computerPlayer2.addCard(deck.hit());
        }
    }
    public void startGame() {
        deck.shuffle(); // Stary by suffer
        player = new Human(1000, 0, new ArrayList<>());
        dealer = new Dealer(new ArrayList<>());
        computerPlayer1 = new ComputerPlayer(new ArrayList<>());
        computerPlayer2 = new ComputerPlayer(new ArrayList<>());
        dealInitialCards();

    }
    public Dealer getDealer() {
        return this.dealer;  // Return the dealer object
    }






    //========== get the image inssde the ressource and display out===========================//
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
    public VBox getGameUI() {
        return gameUI;
    }

    public void saveGame() {}
    public void exitGame() {}
    public void loadGame(String saveStateString) {}



}
