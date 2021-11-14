package controller;

/**
 * Represents an exception triggered when trying to add an object to a list that already exists in it
 */
public class AlreadyExistingException extends RuntimeException{
    public AlreadyExistingException(String message) {
        super(message);
    }
}
