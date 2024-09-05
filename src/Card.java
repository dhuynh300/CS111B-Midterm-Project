import java.util.List;

public class Card {
    // Constants
    private static final List<String> CARD_NAME_LIST =
            List.of("Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King");
    private static final List<String> SUIT_NAME_LIST =
            List.of("Clubs", "Diamonds", "Hearts", "Spades");

    // Member values (final because they should not be changed while in use)
    private final int index;
    private final String cardName;
    private final String suitName;

    // Constructors
    public Card(int newIndex, int suitIndex) {
        if (newIndex < 1)
            System.out.println("newIndex in Card cannot be < 1!");

        if (newIndex > 13)
            System.out.println("newIndex in Card cannot be > 13!");

        if (suitIndex < 0)
            System.out.println("suitIndex in Card cannot be < 0!");

        if (suitIndex > 3)
            System.out.println("suitIndex in Card cannot be > 3!");

        this.index = newIndex; // Assumes valid input range from 1-13 (Aces-Kings)
        this.cardName = CARD_NAME_LIST.get(this.index - 1); // Set card's name in constructor
        this.suitName = SUIT_NAME_LIST.get(suitIndex); // Assumes valid input range from 0-3 (Clubs-Spades)
    }

    // Methods
    public String toString() {
        if (this.index == 1) // Special values for aces
            return this.cardName + " of " + this.suitName + " with a value of 1 or 11";

        return this.cardName + " of " + this.suitName + " with a value of " + this.getCardValue();
    }

    // Returns a card's value, 2-10 for regular cards, 10 for face cards, and ace's special values are handled where this method is used (else returns 1)
    public int getCardValue() {
        // Under normal play, this should have values from 1-10 for non-ace cards
        return Math.min(this.index, 10);
    }

    // Getters (for all) and setters (for non-final values)
    public int getIndex() {
        return this.index;
    }

    public String getCardName() {
        return this.cardName;
    }

    public String getSuitName() {
        return this.suitName;
    }
}
