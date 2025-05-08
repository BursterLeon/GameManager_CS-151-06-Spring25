package blackjack;

import blackjack.Card.Rank;
import java.util.List;

public class Dealer extends Player {

    private final Deck deck;

    public Dealer(List<Card> playerHand, Deck deck) {
        super(playerHand);
        this.deck = deck;
    }

    public Deck getDeck() {
        return deck;
    }

    // Dealer hits if hand value is below 17 or has a soft 17 until bust or stand
    public void playTurn(Deck deck) {

        while(this.getPlayerHandTotal() < 17 || isSoft17()) {
            Card drawnCard = deck.hit();
            addCard(drawnCard);
            System.out.println("Dealer hits and draws " + drawnCard);
            int total = getPlayerHandTotal();
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

    // Deal method
    public void deal(Player player) {
        player.addCard(deck.hit());
    }

    public void revealSecondCard() {
        System.out.println(getPlayerHand().get(1));
    }

    @Override
    public String toString() {
        return "Dealer";
    }

}