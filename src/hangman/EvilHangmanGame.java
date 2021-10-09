package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    //String myPattern;
    int myWordLength;

    private TreeSet<String> myWords = new TreeSet<>();

    private SortedSet<Character> lettersGuessed = new TreeSet<>();
    private Set<String> myEvilSetOfWords = new HashSet<>();

    private String globalWord = new String();

    private int numberOfGuesses = 0;


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

        noGuessFirstPrint();

        myEvilSetOfWords.addAll(myWords);
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);

        //Check if guess is not a number
        if (!(Character.isAlphabetic(guess))) {
            System.out.println("Wrong input! Numbers and special characters are not accepted.");
            return myEvilSetOfWords;
        }

        //Check if guess was already made
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
            } else {
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
            } else if (guessedWords.get(key).size() == allPossibleWords.size()) {
                if (maxDashes < numberOfDashes) {
                    maxDashes = numberOfDashes;
                    biggestKey = key;
                    allPossibleWords = guessedWords.get(biggestKey);
                } else if (maxDashes == numberOfDashes) {
                    //right-most algorithm

                    for (int i = 0; i < biggestKey.length(); i++) {
                        if (biggestKey.charAt(i) != key.charAt(i)) {
                            if (biggestKey.charAt(i) == '-') {
                                allPossibleWords = guessedWords.get(biggestKey);
                            } else if (key.charAt(i) == '-') {
                                allPossibleWords = guessedWords.get(key);
                                biggestKey = key;
                            }
                            break;
                        }
                    }
                }
            }
        }

        if (maxDashes == myWordLength) {
            System.out.println("Sorry, the letter " + guess + " is not in the word.");
            numberOfGuesses--;
        } else if (maxDashes == myWordLength - 1) {
            System.out.println("Yes, there is 1 " + guess + " in the word!");
        } else {
            System.out.println("Yes, there are " + (myWordLength - maxDashes) + " " + guess + "'s in the word!!");
        }

        globalWord = buildWord(biggestKey, guess);

        //call evilWords and make it equal to PossibleWords
        myEvilSetOfWords = allPossibleWords;

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

        globalWord = dashes.toString();

        return dashes.toString();
    }

    public String makeKey(String word, Character guessChar) {
        StringBuilder j = new StringBuilder();

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

    private String buildWord(String aKey, char userGuess) {
        StringBuilder myWord = new StringBuilder();

        myWord.append(globalWord);

        for (int i = 0; i < aKey.length(); i++) {
            if (aKey.charAt(i) == userGuess) {
                myWord.setCharAt(i, userGuess);
            }
        }

        return myWord.toString();
    }

    public String getBuiltWord() {
        return globalWord;
    }

    public void setNumberOfGuesses(int numberGuesses) {
        this.numberOfGuesses = numberGuesses;
    }

    public int getNumberOfGuesses() {
        return this.numberOfGuesses;
    }

    public String getAFinalWord() {
        return myEvilSetOfWords.iterator().next().toString();
    }
}
