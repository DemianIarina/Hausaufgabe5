package controller;

public class TooManyCreditsException extends RuntimeException{
    public TooManyCreditsException(String message) {
        super(message);
    }
}
