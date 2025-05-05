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

    public void placeBet() {

    }

    //constructor

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

    }


    public void startGame() {

    }
    public void saveGame() {}
    public void exitGame() {}
    public void loadGame(String saveStateString) {}



}
