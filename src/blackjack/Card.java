package blackjack;

public class Card {

    public static enum Suit {
        diamonds, hearts, clubs, spades;
    }

    public static enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), J(10), Q(10), K(10),
        A(11);
        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final Suit suit;
    private final Rank rank;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String toString() {
        if (rank.name().equals("J")) {
            return suit + "_" + rank.name();
        } else if (rank.name().equals("Q")) {
            return suit + "_" + rank.name();
        } else if (rank.name().equals("K")) {
            return suit + "_" + rank.name();
        } else if (rank.name().equals("A")) {
            return suit + "_" + rank.name();
        } else {
            return suit + "_" + rank.getValue();
        }

    }
}

