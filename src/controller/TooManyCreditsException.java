package controller;

/**
 * Represents an exception triggered when the limit of credits has been reached
 */
public class TooManyCreditsException extends RuntimeException{
    public TooManyCreditsException(String message) {
        super(message);
    }
}
