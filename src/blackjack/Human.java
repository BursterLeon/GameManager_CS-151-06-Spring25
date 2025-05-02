
package blackjack;

import java.util.List;
import java.util.Scanner;

public class Human extends Player {
    private int playerBalance;
    private int currentBet;

    public Human(int playerBalance, int currentBet, List<Card> playerHand) {
        super(playerHand);
        this.playerBalance = playerBalance;
        this.currentBet = currentBet;
    }

    public int getPlayerBalance() {
        return this.playerBalance;
    }

    public int getCurrentBet() {
        return this.currentBet;
    }

    public void playTurn(Deck deck) {

    }
}
