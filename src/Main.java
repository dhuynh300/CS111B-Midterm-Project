import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Initialize BlackJack class
        System.out.println("How many decks to use in these games of Blackjack?");
        BlackJack mainBlackJack = new BlackJack(Integer.parseInt(scnr.nextLine()));
        System.out.println(mainBlackJack.getCurrentDeck());

        // LET'S GO GAMBLING!
        char playerInput; // 'y' for again, anything else exits
        do {
            // Gamble core
            mainBlackJack.playGame(scnr);

            // Allow for multiple games
            System.out.println("Play again? (y/n)");
            playerInput = scnr.nextLine().toLowerCase().charAt(0);
        } while (playerInput == 'y');

        System.out.println("Goodbye.");
    }
}