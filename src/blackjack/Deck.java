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

    // Shuffles deck
    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    // Resets deck after each round
    public void reset() {
        this.deck.clear();
        this.createDeck();
    }

    // Card on top of the deck gets removed whenever hit method is called
    public Card hit() {
        return deck.remove(0);
    }

    // Assigning every card a rank and suit
    private void createDeck() {
        for(Suit suit : Suit.values()) {
            for(Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                deck.add(card);
            }
        }
    }

}