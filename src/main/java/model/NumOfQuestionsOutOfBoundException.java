package model;

public class NumOfQuestionsOutOfBoundException extends Exception {

    public NumOfQuestionsOutOfBoundException() {
        super("Invalid input. Please try again.");
    }

}
