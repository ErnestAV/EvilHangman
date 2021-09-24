package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    String myPattern;
    int myWordLength;

    private TreeSet<String> myWords = new TreeSet<>();

    private SortedSet<Character> lettersGuessed = new TreeSet<>();
    private Set<String> myEvilSetOfWords = new HashSet<>();


    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {

        myEvilSetOfWords.clear();;
        lettersGuessed.clear();
        myWords.clear();

        myWordLength = wordLength;

        Scanner newScanner = null;

        newScanner = new Scanner(dictionary);

        while (newScanner.hasNext()) {
            String currentWord = newScanner.next();
            if (currentWord.length() == wordLength) {
                myWords.add(currentWord);
            }
        }

        if (myWords.isEmpty()) {
            throw new EmptyDictionaryException();
        }

        myEvilSetOfWords.addAll(myWords);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);

        if (lettersGuessed.contains(guess)) {
            throw new GuessAlreadyMadeException();
        } else {
            lettersGuessed.add(guess);
        }

        Map<String, Set<String>> guessedWords = new HashMap<>();
        Set<String> allPossibleWords = new HashSet<>();
        allPossibleWords.clear();
        String key = null;
        String biggestKey = null;
        int maxDashes = 0;

        for (String word : myEvilSetOfWords) {
            Set<String> changingPossibleWords = new HashSet<>();
            key = makeKey(word, guess);
            changingPossibleWords.add(word);

            if (guessedWords.containsKey(key)) {
                guessedWords.get(key).add(word);
            }
            else {
                guessedWords.put(key, changingPossibleWords);
            }

            StringBuilder getANewKey = new StringBuilder();
            getANewKey.append(key);

            int numberOfDashes = 0;

            for (int i = 0; i < getANewKey.length(); i++) {
                if (getANewKey.charAt(i) == '-') {
                    numberOfDashes++;
                }
            }

            if (guessedWords.get(key).size() > allPossibleWords.size()) {
                maxDashes = numberOfDashes;
                biggestKey = key;
                allPossibleWords = guessedWords.get(biggestKey);
            }
            else if (guessedWords.get(key).size() == allPossibleWords.size()) {
                if (maxDashes < numberOfDashes) {
                    maxDashes = numberOfDashes;
                    biggestKey = key;
                    allPossibleWords = guessedWords.get(biggestKey);
                }
                else if (maxDashes == numberOfDashes) {

                    for (int i = 0; i < biggestKey.length(); i++) {
                        if (biggestKey.charAt(i) != key.charAt(i)) {
                            if (biggestKey.charAt(i) == '-') {
                                allPossibleWords = guessedWords.get(biggestKey);
                            }
                            else if (key.charAt(i) == '-') {
                                allPossibleWords = guessedWords.get(key);
                                biggestKey = key;
                            }
                            break;
                        }
                    }
                    /**
                    if (max < value) {
                        allPossibleWords = guessedWords.get(key);
                    } **/
                }
            }
        }

        //call evilWords and make it equal to PossibleWords
        myEvilSetOfWords = allPossibleWords;

        //allPossibleWords.clear();

        return myEvilSetOfWords;
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return lettersGuessed;
    }

    public String noGuessFirstPrint() {
        StringBuilder dashes = new StringBuilder();

        for (int i = 0; i < myWordLength; i++) {
            dashes.append('-');
        }

        return dashes.toString();
    }

    public String makeKey(String word, Character guessChar) {
        StringBuilder w = new StringBuilder();
        StringBuilder j = new StringBuilder();

        w.append(word);

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guessChar) {
                j.append(guessChar);
            }
            else {
                j.append('-');
            }
        }

        return j.toString();
    }
}
