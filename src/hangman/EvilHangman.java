package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {
        if (args.length != 3) {
            System.out.println("Invalid arguments");
        }

        /** Variables necessary for the game to be set up **/
        EvilHangmanGame evilHangmanGame = new EvilHangmanGame();
        evilHangmanGame.setNumberOfGuesses(Integer.parseInt(args[2]));
        int wordLength = Integer.parseInt(args[1]);
        File givenFile = new File(args[0]);

        /** Variables neccessary to run game **/
        Scanner myScanner = new Scanner(System.in);
        char userGuess = 0;

        evilHangmanGame.startGame(givenFile, wordLength); //Set up new game
        System.out.println("Welcome, to the Evil Hangman Game!");

        while (evilHangmanGame.getNumberOfGuesses() != 0) {
            System.out.println("You have " + evilHangmanGame.getNumberOfGuesses() + " guesses left");
            System.out.println("Used letters: " + evilHangmanGame.getGuessedLetters());

            System.out.println("Word: " + evilHangmanGame.getBuiltWord()); // figure out how to print the acumulated and updated word

            System.out.print("Enter guess: ");
            userGuess = myScanner.next().charAt(0);

            try {
                evilHangmanGame.makeGuess(userGuess);
            }
            catch (GuessAlreadyMadeException e) {
                System.out.println("Letter already guessed!");
            }

            if (evilHangmanGame.getNumberOfGuesses() != 0 && !evilHangmanGame.getBuiltWord().contains("-")) {
                System.out.println("Congratulations! You won!");
                break;
            } else if (evilHangmanGame.getNumberOfGuesses() == 0) {
                System.out.println("\nYou are out of guesses. \nSorry, you lose!");
            }
        }
    }
}
