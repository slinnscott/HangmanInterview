package hangman;

import java.util.*;

public class HangmanGame{
    int wordLength;
    String[] dictionary = {"shoe", "ignore", "crate", "absorbing", "grape",
        "ludicrous", "system", "guiltless", "tip", "include"};
    String gameWord;
    TreeSet<Character> usedLetters = new TreeSet();
    String key;


    public HangmanGame() {
    }

    public void startGame(){
        int rand = (int) Math.floor(Math.random() * 10);
        gameWord = this.dictionary[rand];
        this.key = "";

        for(int i = 0; i < gameWord.length(); i++){
            this.key = this.key.concat("-");
        }
        this.wordLength = gameWord.length();

    }



    public String getKey() {
        return this.key;
    }


    public void makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if (this.usedLetters.contains(guess)) {
            GuessAlreadyMadeException e = new GuessAlreadyMadeException();
            throw e;
        } else {
            this.usedLetters.add(guess);
            this.key = makeKey(gameWord, guess);
            return;
        }
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





    public SortedSet<Character> getUsedLetters() {
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

    public String getGameWord(){
        return gameWord;
    }



    public String getHangedMan(int incorrectGuesses){

        if(incorrectGuesses >= 10){
            incorrectGuesses = 10;
        }
        String key = String.valueOf(incorrectGuesses);
        Map<String, String> map = new HashMap<String, String>();
        map.put("0", "");
        map.put("1", "    O");
        map.put("2", "    O \n" +
                     "    |");
        map.put("3", "    O \n" +
                     "   -|");
        map.put("4", "    O \n" +
                     "   -|-");
        map.put("5", "    O \n" +
                     "   -|-\n" +
                     "   /");
        map.put("6", "    O \n" +
                     "   -|-\n" +
                     "   / \\");
        map.put("7", "    O \n" +
                     "   -|-\n" +
                     "   / \\\n" +
                     "--------");
        map.put("8", "|   O \n" +
                     "|  -|-\n" +
                     "|  / \\\n" +
                     "|--------");
        map.put("9", " ____\n" +
                     "|   O \n" +
                     "|  -|-\n" +
                     "|  / \\\n" +
                     "|--------");
        map.put("10", " ____\n" +
                     "|    O \n" +
                     "|   -|-\n" +
                     "|   / \\\n" +
                     "|--     --");
        return map.get(key);
    }

}
