package blackjack;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class BlackjackMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Blackjack!");

        List<Card> playerHand = new ArrayList<Card>();
        List<Card> dealerHand = new ArrayList<Card>();
        List<Card> bot1Hand = new ArrayList<Card>();
        List<Card> bot2Hand = new ArrayList<Card>();
        // Create players
        Deck deck = new Deck();
        Human player = new Human(1000, playerHand, 0); // Start with 1000 currency
        Dealer dealer = new Dealer(dealerHand, deck);
        ComputerPlayer bot1 = new ComputerPlayer(bot1Hand);
        ComputerPlayer bot2 = new ComputerPlayer(bot2Hand);

        // Create and configure game
        BlackjackGame game = new BlackjackGame();
        game.setDeck(deck);
        game.setPlayer(player);
        game.setDealer(dealer);
        game.setComputerPlayer1(bot1);
        game.setComputerPlayer2(bot2);

        // Start the game
        game.startGame();

        scanner.close();
        }
    }

//    public void saveGame() {}
//    public void exitGame() {}
//    public void loadGame(String saveStateString) {}
//



