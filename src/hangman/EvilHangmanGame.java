package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    String myPattern;
    int myWordLength;

    private TreeSet<String> myWordsToGuess = new TreeSet<String>();
    private TreeSet<String> lettersGuessed = new TreeSet<String>();
    private TreeSet<String> evilWords = new TreeSet<String>();


    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        myWordLength = wordLength;

        Scanner newScanner = null;

        try {
            newScanner = new Scanner(dictionary);

            while (newScanner.hasNext()) {
                String currentWordToGuess = newScanner.next();
                if (currentWordToGuess.length() == wordLength) {
                    myWordsToGuess.add(currentWordToGuess);
                }
            }
        } catch (FileNotFoundException fileNotFound) {
            fileNotFound.printStackTrace();
            return;
        }

        if (newScanner != null) {
            newScanner.close();
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        String newGuess = Character.toString(guess);



        return null;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }

    public String noGuessFirstPrint() {
        StringBuilder dashes = new StringBuilder();

        for (int i = 0; i < myWordLength; i++) {
            dashes.append('-');
        }

        return dashes.toString();
    }
}
