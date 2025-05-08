package blackjack;

import blackjack.Card.Rank;
import java.util.List;

public class Dealer extends Player {

    public Dealer(List<Card> playerHand) {
        super(playerHand);
    }

    public void playTurn(Deck deck) {
        // Make sure if is under 15 it can keep hit
        System.out.println("Dealer starts with total: " + getPlayerHandTotal() + ", hand: " + getPlayerHand());
        while(this.getPlayerHandTotal() < 15 || isSoft17()) {
            Card drawnCard = deck.hit();
            this.getPlayerHand().add(drawnCard);
            System.out.println("Dealer hits and draws " + drawnCard);
        }

        System.out.println("Dealer stands at " + getPlayerHandTotal());
    }

    // Check for soft 17 (Dealer has total value of 17 and an ace card)
    private boolean isSoft17() {
        int total = getPlayerHandTotal();
        boolean hasAce = false;
        int sum = 0;

        for(Card card : getPlayerHand()) {
            sum += card.getRank().getValue();
            if(card.getRank() == Rank.ACE) {
                hasAce = true;
            }
        }

        if(total == 17 && hasAce && sum > 17) {
            return true;
        } else {
            return false;
        }

    }
    public boolean shouldHit() {
        return this.getPlayerHandTotal() < 15;
    }

    public void hit(Deck deck) {
        Card drawnCard = deck.hit();
        this.getPlayerHand().add(drawnCard);
        System.out.println("Dealer hits " + drawnCard + ", total: " + getPlayerHandTotal());
    }

}