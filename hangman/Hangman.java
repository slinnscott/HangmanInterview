package hangman;

import java.util.Scanner;

public class Hangman {
    public Hangman() {
    }

    public static void main(String[] args) {
        boolean quitGame = false;
        while(!quitGame) {
            int correctGuesses = 0;
            int wrongGuesses = 0;
            HangmanGame game = new HangmanGame();

            game.startGame();
            boolean gameFinished = false;
            System.out.println("There are " + game.getKey().length() + " letters in this word!");

            String key;
            while (!gameFinished) {
                String img = game.getHangedMan(wrongGuesses);
                System.out.println();
                System.out.println("Image:");
                System.out.println(img);
                System.out.println();
                System.out.println("You have made " + correctGuesses + " correct guesses and " + wrongGuesses + " wrong guesses");
                System.out.println("Used letters: " + game.printUsedLetters());
                System.out.println("Word: " + game.getKey());
                System.out.print("Enter Guess: ");


                Scanner cmdLine = new Scanner(System.in);
                String stringGuess = cmdLine.next();
                char c;

                try {
                    c = game.checkValidInput(stringGuess);
                } catch (InvalidInputException var2) {
                    var2.printException();
                    continue;
                }


                try {
                    game.makeGuess(c);
                    key = game.getKey();
                    if (!key.contains(Character.toString(c))) {
                        System.out.println("Sorry, there are no " + c + "'s");
                        wrongGuesses++;
                    } else {
                        int count = 0;

                        for (int i = 0; i < key.length(); ++i) {
                            if (key.charAt(i) == c) {
                                ++count;
                            }
                        }

                        System.out.println("Yes, there is " + count + " " + c + "'s");
                        correctGuesses++;
                        if (!key.contains("-")) {
                            gameFinished = true;
                        }
                    }
                } catch (GuessAlreadyMadeException var15) {
                    var15.printException();
                }
            }

            key = game.getKey();
            if (!key.contains("-")) {
                System.out.println("You win!");
                System.out.println("The word was: " + key);
                System.out.println("You found the word in " + correctGuesses + " correct guesses and " + wrongGuesses + " wrong guesses");
                int totalGuesses = correctGuesses + wrongGuesses;
                System.out.println("That's a total of " + totalGuesses + "!");
            } else {
                System.out.println("You lose!");
                System.out.println("The word was: " + game.getGameWord());
            }

            System.out.println();
            System.out.println("Would you like to play again? (yes/no)");
            Scanner cmdLine = new Scanner(System.in);
            String response = cmdLine.next();

            if(response.equals("no") || response.equals("No")){
                quitGame = true;
            }
        }

        System.out.println("Thanks for playing!");
    }
}