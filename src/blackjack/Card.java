package blackjack;
public class Card {
    private String rank;
    private String suit;
    private int value;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        setValue();
    }

    // Value of each card
    private void setValue() {
        switch (rank) {
            case "A": //A value 11 base on black jack rule
                value = 11;
                break;
            case "K":
            case "Q":
            case "J":
                value = 10;
                break;
            default:
                value = Integer.parseInt(rank);
        }
    }

    // Get and set the value
    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    // Override
    @Override
    public String toString() {
        return rank + " of " + suit;
    }


}

