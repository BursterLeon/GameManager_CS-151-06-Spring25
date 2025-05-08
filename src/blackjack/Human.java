
package blackjack;

import java.util.List;
import java.util.Scanner;

public class Human extends Player {
    private int playerBalance;
    private int highScore;


    public Human(int playerBalance, List<Card> playerHand, int highScore) {
        super(playerHand);
        this.playerBalance = playerBalance;
        this.highScore = highScore;

    }

    public int getPlayerBalance() {
        return this.playerBalance;
    }

    public void setPlayerBalance(int playerBalance) {
        this.playerBalance = playerBalance;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    // Player has option to hit or stand (using mouse)
    public void playTurn(Deck deck) {
        int total = getPlayerHandTotal();
        // player should have option to click on hit or stand. loop exits if player busts or stands
        while(!bust()) {
            total = getPlayerHandTotal();
            // if user clicks on hit
            Card drawnCard = deck.hit();
            addCard(drawnCard);


            // exits if player stands or busts
            // break;
        }

    }

    // Takes user bet before the round starts (Needs drop down option for player bet)
    public boolean placeBet(int bet) {
        Scanner scanner = new Scanner(System.in);
        int balance = getPlayerBalance();

        System.out.println("Select your bet: ");
        System.out.println("$1, $5, $25, $100, $500, $1000");

        // Loops until user enters a valid bet
        while (true) {
            System.out.print("Your bet: $");
            int userBet = scanner.nextInt();

            // playerBet = whatever user clicks on
            if (userBet <= balance && isValidBet(userBet)) {
                bet = userBet;
                balance -= bet;
                setPlayerBalance(balance);
                scanner.close();
                return true;
            } else {
                System.out.println("Invalid bet.");
            }

        }
    }

    // Checking for valid bet from user
    private boolean isValidBet(int bet) {
        return bet == 1 || bet == 5 || bet == 25 || bet == 100 || bet == 500 || bet == 1000;
    }

    // Gives doubled the bet back to the user if they win the round
    public void winBet(int bet) {
        playerBalance += bet * 2;
    }

    // Confirms loss
    public void loseBet(int bet) {}


    @Override
    public String toString() {
        return "You";
    }
}
