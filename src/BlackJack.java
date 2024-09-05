import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJack {
    // Constants
    private final int BLACKJACK_VALUE = 21;
    private final int ACES_INDEX = 1;
    private final int ACES_VALUE_ELEVEN = 11;

    // Member values
    private int dealerWins;
    private int playerWins;
    private int decksUsed;
    // Final as these do not need to change objects
    private final List<Card> dealerCards;
    private final List<Card> playerCards;
    private final Deck currentDeck;

    // Constructors
    public BlackJack(int newDecksUsed) {
        if (newDecksUsed < 1)
            System.out.println("newDecksUsed in BlackJack() in BlackJack cannot be < 1!");

        this.dealerWins = 0;
        this.playerWins = 0;
        this.decksUsed = newDecksUsed;
        this.dealerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
        this.currentDeck = new Deck(this.decksUsed);
    }

    // Methods
    public String toString() {
        return "BlackJack object with dealer wins = " + this.dealerWins + " and player wins = " + this.playerWins;
    }

    public void resetHandsAndDeck() {
        this.dealerCards.clear();
        this.playerCards.clear();
        this.currentDeck.resetDeck(this.decksUsed);
    }

    // Play one game of blackjack
    public void playGame(Scanner scnr) {
        if (scnr == null) {
            System.out.println("scnr in playGame() in BlackJack cannot be null!");
            return;
        }

        // Reset cards
        this.resetHandsAndDeck();

        // Draw initial cards
        for (int i = 0; i < 2; i++) {
            this.drawCard(this.playerCards);
            this.drawCard(this.dealerCards);
        }

        // Check for blackJack on first serve
        boolean playerBlackJacked = this.playerBlackJacked(this.calculateHandValue(this.playerCards));
        boolean dealerBlackJacked = this.playerBlackJacked(this.calculateHandValue(this.dealerCards));
        if (playerBlackJacked || dealerBlackJacked)
            this.printHands(true);

        if (playerBlackJacked && dealerBlackJacked) {
            System.out.println("Push!");
        } else if (playerBlackJacked) {
            System.out.println("Player got 21!");
            this.playerWins++;
        } else if (dealerBlackJacked) {
            System.out.println("Dealer got 21!");
            this.dealerWins++;
        } else {
            // Else continue to play blackjack
            int playerHandValue;

            // Ask user for hit or stand
            char playerInput; // 'h' for hit 's' for stand
            do {
                // Show current hand
                this.printHands(false);

                // Check for valid user input
                do {
                    System.out.println("Hit or Stand? (h/s)");
                    playerInput = scnr.nextLine().toLowerCase().charAt(0);
                } while (playerInput != 'h' && playerInput != 's');

                // Add and announce new card
                if (playerInput == 'h')
                    System.out.println("Player drew a " + this.drawCard(this.playerCards) + ".");

                // Recalculate hand value
                playerHandValue = this.calculateHandValue(this.playerCards);
            } while (playerInput != 's' && playerHandValue <= BLACKJACK_VALUE);

            // Show current hands
            this.printHands(true);

            // Check if player busted
            if (playerHandValue > BLACKJACK_VALUE) {
                System.out.println("Player Busted!");
                this.dealerWins++;
            } else {
                // Dealer must draw until hand value >= 17
                int dealerHandValue = this.calculateHandValue(this.dealerCards);
                if (dealerHandValue >= 17)
                    System.out.println("Dealer drew nothing...");

                while (dealerHandValue < 17) {
                    System.out.println("Dealer drew a " + this.drawCard(this.dealerCards) + ".");
                    dealerHandValue = this.calculateHandValue(this.dealerCards);
                }

                // Print current hands first then the winner
                this.printHands(true);
                if (dealerHandValue > BLACKJACK_VALUE) {
                    System.out.println("Dealer busted!");
                    this.playerWins++;
                } else if (playerHandValue == dealerHandValue) {
                    System.out.println("Push!");
                } else if (playerHandValue > dealerHandValue) {
                    System.out.println("Player wins!");
                    this.playerWins++;
                } else {
                    System.out.println("Dealer wins!");
                    this.dealerWins++;
                }
            }
        }

        // Print scores
        this.printWins();
    }

    // Calculate a player's hand value and auto chooses 1 or 11 for ace cards
    public int calculateHandValue(List<Card> user) {
        if (user == null) {
            System.out.println("user in calculateHandValue() in BlackJack cannot be null!");
            return -1;
        }

        int handValue = 0;
        int acesInHand = 0;
        for (Card card : user) {
            // Check for aces to calculate at the end
            if (card.getIndex() == ACES_INDEX) {
                acesInHand++;
            } else {
                handValue += card.getCardValue();
            }
        }

        // Auto choose aces values at the end as if it chooses in the middle of the deck then it will sometimes bust
        for (int i = 0; i < acesInHand; i++) {
            if (handValue + ACES_VALUE_ELEVEN <= BLACKJACK_VALUE) {
                handValue += ACES_VALUE_ELEVEN;
            } else {
                handValue++;
            }
        }

        return handValue;
    }

    // Draw a card for the user and also return it
    public Card drawCard(List<Card> user) {
        if (user == null) {
            System.out.println("user in drawCard() in BlackJack cannot be null!");
            return null;
        }

        Card newCard = this.currentDeck.drawAndRemoveRandomCard();
        user.add(newCard);
        return newCard;
    }

    // Checks if a player's hand value is 21/blackjack
    public boolean playerBlackJacked(int handValue) {
        return handValue == BLACKJACK_VALUE;
    }

    // Show the player the current status of the game
    public void printHands(boolean showAllDealerCards) {
        // Dealer
        System.out.println("====================================================================================================");
        if (showAllDealerCards) {
            System.out.println("Dealer's cards: ");
            for (Card card : this.dealerCards)
                System.out.print("(" + card + ")");

            System.out.println("\n= " + this.calculateHandValue(this.dealerCards));
        } else {
            Card shownCard = this.dealerCards.getFirst();
            System.out.println("Dealer's shown cards: ");
            System.out.print("(" + shownCard + ")");
            System.out.println("\n= " + shownCard.getCardValue());
        }

        // Player
        System.out.println("\nPlayer's cards: ");
        for (Card card : this.playerCards)
            System.out.print("(" + card + ")");

        System.out.println("\n= " + this.calculateHandValue(this.playerCards));
        System.out.println("====================================================================================================");
    }

    public void printWins() {
        System.out.println("Dealer: " + this.dealerWins + ", Player: " + this.playerWins);
    }

    // Getters (for all) and setters (for non-final values)
    public int getDealerWins() {
        return dealerWins;
    }

    // I don't mind dealer wins being any number
    public void setDealerWins(int newWins) {
        this.dealerWins = newWins;
    }

    public int getPlayerWins() {
        return playerWins;
    }

    // I don't mind player wins being any number
    public void setPlayerWins(int newWins) {
        this.playerWins = newWins;
    }

    public int getDecksUsed() {
        return this.decksUsed;
    }

    public void setDecksUsed(int newDecksUsed) {
        if (newDecksUsed < 1) {
            System.out.println("newDecksUsed in setDecksUsed() in BlackJack cannot be < 1!");
            newDecksUsed = 1;
        }

        this.decksUsed = newDecksUsed;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }
}
