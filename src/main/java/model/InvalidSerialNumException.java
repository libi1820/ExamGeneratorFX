package model;

public class InvalidSerialNumException extends Exception {

    public InvalidSerialNumException() {

        super("Invalid serial number. Please try again.");
    }

}
