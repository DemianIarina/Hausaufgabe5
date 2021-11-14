package controller;

public class NonexistentArgumentException extends RuntimeException{
    public NonexistentArgumentException(String message) {
        super(message);
    }
}
