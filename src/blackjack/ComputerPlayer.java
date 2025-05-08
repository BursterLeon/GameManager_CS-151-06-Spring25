package blackjack;

import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random = new Random();

    public ComputerPlayer(List<Card> playerHand) {
        super(playerHand);
    }

    // Computer player stands if hand is 17 or higher, hits otherwise
    public void playTurn1(Deck deck) {
        while(getPlayerHandTotal() < 16) {
            Card drawnCard = deck.hit();
            getPlayerHand().add(drawnCard);
            System.out.println("Computer Player 1 hits and draws " + drawnCard);
        }

        System.out.println("Computer Player 1 stands at " + getPlayerHandTotal());
    }

    // Computer player has random chance of hitting at a certain hand total
    public void playTurn2(Deck deck) {
        int total = this.getPlayerHandTotal();

        while(true) {
            // Computer player stands if hand total is 20 or blackjack
            if (total > 19) {
                System.out.println("Computer Player 2 stands at " + total);
                break;
            }

            // Computer player chance has a 50% of hitting if hand total is between 15 and 18
            else if(total > 14 && total < 19) {
                if (random.nextBoolean()) {
                    System.out.println("Computer Player 2 stands at " + total);
                    break;
                } else {
                    Card drawnCard = deck.hit();
                    getPlayerHand().add(drawnCard);
                    System.out.println("Computer Player 2 hits and draws " + drawnCard);
                }
            }

            // Computer player will hit if hand total is 14 or lower
            else if (total <= 14) {
                Card drawnCard = deck.hit();
                getPlayerHand().add(drawnCard);
                System.out.println("Computer Player 2 hits and draws " + drawnCard);
            } else {
                System.out.println("Computer Player 2 stands at " + total);
                break;
            }
        }
    }
}