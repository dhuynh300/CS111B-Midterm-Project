import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    // Constants
    private static final List<Card> STANDARD_52_DECK =
            List.of(new Card(1, 0), new Card(2, 0), new Card(3, 0), new Card(4, 0), new Card(5, 0), new Card(6, 0), new Card(7, 0), new Card(8, 0), new Card(9, 0), new Card(10, 0), new Card(11, 0), new Card(12, 0), new Card(13, 0),
                    new Card(1, 1), new Card(2, 1), new Card(3, 1), new Card(4, 1), new Card(5, 1), new Card(6, 1), new Card(7, 1), new Card(8, 1), new Card(9, 1), new Card(10, 1), new Card(11, 1), new Card(12, 1), new Card(13, 1),
                    new Card(1, 2), new Card(2, 2), new Card(3, 2), new Card(4, 2), new Card(5, 2), new Card(6, 2), new Card(7, 2), new Card(8, 2), new Card(9, 2), new Card(10, 2), new Card(11, 2), new Card(12, 2), new Card(13, 2),
                    new Card(1, 3), new Card(2, 3), new Card(3, 3), new Card(4, 3), new Card(5, 3), new Card(6, 3), new Card(7, 3), new Card(8, 3), new Card(9, 3), new Card(10, 3), new Card(11, 3), new Card(12, 3), new Card(13, 3));

    // Member values (final because they should not be changed to another object while in use)
    private final List<Card> currentDeck;

    // Constructors
    public Deck(int decksUsed) {
        if (decksUsed < 1)
            System.out.println("decksUsed in Deck cannot be < 1!");

        this.currentDeck = new ArrayList<>();
        this.resetDeck(decksUsed);
    }

    // Methods
    public String toString() {
        return this.getCurrentDeckSize() + " card deck";
    }

    // Clear deck and add "decksUsed" amount of decks (total cards = 52 * decksUsed)
    public void resetDeck(int decksUsed) {
        if (decksUsed < 1)
            System.out.println("decksUsed in Deck cannot be < 1!");

        decksUsed = Math.max(decksUsed, 1); // Clamp decksUsed so that it can never be below 1

        this.currentDeck.clear();
        for (int i = 0; i < decksUsed; i++)
            this.currentDeck.addAll(STANDARD_52_DECK);
    }

    // Randomly choose a card from the deck (which is better than scrambling the deck in "resetDeck()") and also remove it from currentDeck
    public Card drawAndRemoveRandomCard() {
        // Error checking against an empty deck
        if (this.getCurrentDeckSize() <= 0)
            return null;

        Random rng = new Random(); // "This constructor sets the seed of the random number generator to a value very likely to be distinct from any other invocation of this constructor"
        Card drawnCard = this.currentDeck.get(rng.nextInt(this.getCurrentDeckSize()));
        this.currentDeck.remove(drawnCard);
        return drawnCard;
    }

    public int getCurrentDeckSize() {
        return this.currentDeck.size();
    }

    // Getters (for all) and setters (for non-final values)
    private List<Card> getCurrentDeck() {
        return this.currentDeck;
    }
}
