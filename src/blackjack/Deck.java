package blackjack;

import blackjack.Card.Rank;
import blackjack.Card.Suit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> deck = new ArrayList();

    public Deck() {
        this.createDeck();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    public void reset() {
        this.deck.clear();
        this.createDeck();
    }

    public Card hit() {
        return deck.remove(0);
    }

    private void createDeck() {
        for(Suit suit : Suit.values()) {
            for(Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                deck.add(card);
            }
        }
    }

}