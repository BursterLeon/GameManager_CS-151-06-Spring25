package blackjack;

import blackjack.Card;
import blackjack.Card.Rank;
import java.util.List;

public abstract class Player {
    private List<Card> playerHand;

    public Player(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public List<Card> getPlayerHand() {
        return this.playerHand;
    }

    public int getPlayerHandTotal() {
        int total = 0;
        int aces = 0;

        for(Card card : playerHand) {
            total += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                ++aces;
            }

            if (total > 21 && aces > 0) {
                total -= 10;
                --aces;
            }
        }

        return total;
    }

    public void addCard(Card card) {
        this.playerHand.add(card);
    }

    public boolean bust() {
        return getPlayerHandTotal() > 21;
    }



}
