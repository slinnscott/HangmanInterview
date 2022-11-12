package hangman;

public class InvalidInputException extends Exception {
    public InvalidInputException() {
    }

    public void printException() {
        System.out.println("Invalid Input");
    }
}
