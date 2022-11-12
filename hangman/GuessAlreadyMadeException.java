package hangman;

public class GuessAlreadyMadeException extends Exception {
    public GuessAlreadyMadeException() {
    }

    public void printException() {
        System.out.println("This letter has already been guessed");
    }
}
