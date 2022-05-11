package model;

public class InvalidAnswerNumException extends Exception {
    public InvalidAnswerNumException() {
        super("Invalid answer number. Please try again.");
    }
}
