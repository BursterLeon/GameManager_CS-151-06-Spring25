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
    private ComputerPlayer botPlayer1;
    private ComputerPlayer botPlayer2;
    private Turn currentTurn;












    public enum Turn {
        HUMAN, BOT1, BOT2, DEALER;
    }










    //constructor
    public BlackjackMain() {
        this.deck = new Deck();
        this.player = new Human(1000, 0, new ArrayList<>());
        this.dealer = new Dealer(new ArrayList<>());
        this.botPlayer1 = new ComputerPlayer(new ArrayList<>());
        this.botPlayer2= new ComputerPlayer(new ArrayList<>());
        this.currentTurn = Turn.HUMAN;
        deck.shuffle();
        dealInitialCards();
    }












    // All players will play their hand once it's their turn
    public void playTurn() {




        switch(currentTurn) {
            case HUMAN:
                player.playTurn(deck);
                break;
            case BOT1:
                botPlayer1.playTurn1(deck);
                break;
            case BOT2:
                botPlayer2.playTurn2(deck);
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
    public Turn getCurrentTurn() {
        return currentTurn;
    }










    // Return result base on current score
    public String checkPlayerHand() {
        int playerTotal = player.getPlayerHandTotal();
        int dealerTotal = dealer.getPlayerHandTotal();




        if (playerTotal == 21 && player.getPlayerHand().size() == 2) {
            return "Blackjack! You win!";
        } else if (player.bust()) {
            return "You busted with " + playerTotal + ". You lose!";
        } else if (dealer.bust()) {
            return "Dealer busts with " + dealerTotal + ". You win!";
        } else if (playerTotal == dealerTotal) {
            return "Push! It's a draw!";




        }else if (playerTotal > dealerTotal) {
            return "You have " + playerTotal + ", Dealer has " + dealerTotal + ". You win!";
        }
        else {
            return "You have " + playerTotal + ", Dealer has " + dealerTotal + ". You lose!";
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
        botPlayer1 = new ComputerPlayer(new ArrayList<>());
        botPlayer2 = new ComputerPlayer(new ArrayList<>());
        dealInitialCards();
    }




    // Give 2 card for the player
    public void dealInitialCards() {
        for (int i = 0; i < 2; i++) {
            player.addCard(deck.hit()); // Loop 2 time to give 2 card for each plater
            dealer.addCard(deck.hit());
            botPlayer1.addCard(deck.hit());
            botPlayer2.addCard(deck.hit());
        }
    }
    public void startGame() {
        resetGame();




    }
    public Dealer getDealer() {
        return dealer;  // Return the dealer object
    }
    public Human getPlayer() {
        return player;
    }


    // thiss reset all state player,
    public void resetGame() {
        this.deck = new Deck();
        this.player = new Human(1000, 0, new ArrayList<>());
        this.dealer = new Dealer(new ArrayList<>());
        this.botPlayer1 = new ComputerPlayer(new ArrayList<>()); // Create a new arrrat list
        this.botPlayer2 = new ComputerPlayer(new ArrayList<>());
        this.currentTurn = Turn.HUMAN;
        deck.shuffle();// Shuffle again to make new game
        dealInitialCards();
    }


















    //========== get the image inssde the ressource and display out===========================//




    public ComputerPlayer getBotPlayer1() {
        return botPlayer1;
    }
    public ComputerPlayer getBotPlayer2() {
        return botPlayer2;
    }
    public void saveGame() {}
    public void exitGame() {}
    public void loadGame(String saveStateString) {}










}
