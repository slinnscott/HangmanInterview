package hangman;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class HangmanGame{
    int wordLength;
    TreeSet<String> words;
    TreeSet<Character> usedLetters = new TreeSet();
    String key;

    public EvilHangmanGame() {
    }

    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        this.words = new TreeSet();
        Scanner s = new Scanner(dictionary);

        while(s.hasNext()) {
            String token = s.next();
            if (token.length() == wordLength) {
                this.words.add(token);
            }
        }

        if (this.words.size() == 0) {
            EmptyDictionaryException e = new EmptyDictionaryException();
            throw e;
        } else {
            this.wordLength = wordLength;
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < wordLength; ++i) {
                sb.append("-");
            }

            this.key = sb.toString();
        }
    }

    public String getKey() {
        return this.key;
    }

    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if (this.usedLetters.contains(guess)) {
            GuessAlreadyMadeException e = new GuessAlreadyMadeException();
            throw e;
        } else {
            this.usedLetters.add(guess);
            Map<String, TreeSet<String>> groups = new HashMap();

            String word;
            String tempKey;
            for(Iterator var3 = this.words.iterator(); var3.hasNext(); ((TreeSet)groups.get(tempKey)).add(word)) {
                word = (String)var3.next();
                tempKey = this.makeKey(word, guess);
                if (!groups.containsKey(tempKey)) {
                    groups.put(tempKey, new TreeSet());
                }
            }

            Map<String, TreeSet<String>> groups = this.getLargestGroups(groups);
            return this.getPriorityGroup(groups, guess);
        }
    }

    private TreeSet<String> getPriorityGroup(Map<String, TreeSet<String>> og, char guess) {
        int minValue = 2000000000;
        Iterator var4 = og.keySet().iterator();

        while(var4.hasNext()) {
            String tempKey = (String)var4.next();
            int count = 0;
            int weight = 1;
            int weightedValue = 0;

            for(int i = tempKey.length() - 1; i >= 0; --i) {
                if (tempKey.charAt(i) == guess) {
                    ++count;
                    weightedValue += count * weight;
                }

                weight *= 2;
            }

            if (weightedValue < minValue) {
                this.key = tempKey;
                minValue = weightedValue;
            }
        }

        this.words = (TreeSet)og.get(this.key);
        return this.words;
    }

    private Map<String, TreeSet<String>> getLargestGroups(Map<String, TreeSet<String>> og) {
        int max = 0;
        Iterator var4 = og.keySet().iterator();

        TreeSet thisSet;
        while(var4.hasNext()) {
            String key = (String)var4.next();
            thisSet = (TreeSet)og.get(key);
            if (thisSet.size() > max) {
                max = thisSet.size();
            }
        }

        Map<String, TreeSet<String>> newGroup = new HashMap();
        Iterator var8 = og.keySet().iterator();

        while(var8.hasNext()) {
            String key = (String)var8.next();
            thisSet = (TreeSet)og.get(key);
            if (thisSet.size() == max) {
                newGroup.put(key, thisSet);
            }
        }

        return newGroup;
    }

    private String makeKey(String word, char c) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < word.length(); ++i) {
            if (word.charAt(i) == c) {
                sb.append(c);
            } else {
                sb.append(this.key.charAt(i));
            }
        }

        String newKey = sb.toString();
        return newKey;
    }

    public SortedSet<Character> getGuessedLetters() {
        return this.usedLetters;
    }

    public String printUsedLetters() {
        StringBuilder sb = new StringBuilder();
        Iterator var2 = this.usedLetters.iterator();

        while(var2.hasNext()) {
            Character c = (Character)var2.next();
            sb.append(c);
            sb.append(" ");
        }

        String output = sb.toString();
        return output;
    }

    public char checkValidInput(String input) throws InvalidInputException {
        if (input.length() > 1) {
            InvalidInputException e = new InvalidInputException();
            throw e;
        } else {
            char c = input.charAt(0);
            if (!Character.isAlphabetic(c)) {
                InvalidInputException e = new InvalidInputException();
                throw e;
            } else {
                return c;
            }
        }
    }

    public String getRandomWord() {
        Iterator var1 = this.words.iterator();
        if (var1.hasNext()) {
            String string = (String)var1.next();
            return string;
        } else {
            return null;
        }
    }
}
