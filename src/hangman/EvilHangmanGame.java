package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    String myPattern;
    int myWordLength;

    TreeSet<String> myWordsToGuess = new TreeSet<String>();
    SortedSet<String> lettersGuessed = new TreeSet<String>();
    Map<String, Set<String>> myWordMap = new TreeMap<String, Set<String>>();

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
        }

        if (newScanner != null) {
            newScanner.close();
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        String newGuess = Character.toString(guess);
        boolean guessAlreadyMade = false;
        boolean endOfGuess = false;

        while (guessAlreadyMade || endOfGuess) {
            if (lettersGuessed.contains(newGuess)) {
                guessAlreadyMade = true;
            }
            lettersGuessed.add(newGuess);
            //build map
            
        }

        return null;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return null;
    }

    public String noGuessPrint() {
        StringBuilder dashes = new StringBuilder();

        for (int i = 0; i < myWordLength; i++) {
            dashes.append('-');
        }

        return dashes.toString();
    }
}
