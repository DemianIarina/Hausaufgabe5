package controller;

/**
 * Represents an exception triggered when the limit of credits has been reached
 */
public class TooManyCreditsException extends RuntimeException{

    private final int id;

    public TooManyCreditsException(String message, int id) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
