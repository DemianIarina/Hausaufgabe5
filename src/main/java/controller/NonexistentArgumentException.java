package controller;

/**
 * Represents an exception triggered when the given argument does not exist in the repo
 */
public class NonexistentArgumentException extends RuntimeException{
    public NonexistentArgumentException(String message) {
        super(message);
    }
}
