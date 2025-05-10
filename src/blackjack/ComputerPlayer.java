package blackjack;

import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random = new Random();

    public ComputerPlayer(List<Card> playerHand) {
        super(playerHand);
    }

    // hits í 16 higher
    public void playTurn1(Deck deck) {
        while(getPlayerHandTotal() < 16) {
            Card drawnCard = deck.hit();
            getPlayerHand().add(drawnCard);
            System.out.println("Computer Player 1 hits and draws " + drawnCard);
        }

        System.out.println("Computer Player 1 stands at " + getPlayerHandTotal());
    }

    //  computer hand total logic hut
    public void playTurn2(Deck deck) {


        while(true) {
            int total = this.getPlayerHandTotal();
            //satnd if is larger thenan 19
            if (total > 19) {
                System.out.println("Computer Player 2 stands at " + total);
                break;
            }

            // if is  between 15 and 18 it can be hut
            else if(total > 14 && total < 19) {
                if (random.nextBoolean()) {
                    System.out.println("Computer Player 2 stands at " + total);
                    break;
                } else {
                    try {
                        Card drawnCard = deck.hit();
                        getPlayerHand().add(drawnCard);
                        System.out.println("Computer Player 2 hits and draws " + drawnCard);
                    } catch (IllegalStateException e) {
                        System.out.println("Computer Player 2 cannot hit: " + e.getMessage());
                        break;
                    }
                }
            }

            // Player bot will hit when is under 14
            else if (total <= 14) {
                try {
                    Card drawnCard = deck.hit();
                    getPlayerHand().add(drawnCard);
                    System.out.println("Computer Player 2 hits and draws " + drawnCard);
                } catch (IllegalStateException e) {
                    System.out.println("Computer Player 2 cannot hit: " + e.getMessage());
                    break;
                }
        }
    }}}