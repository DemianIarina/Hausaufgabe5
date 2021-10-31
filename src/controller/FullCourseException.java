package controller;

/**
 * Represents an exception triggered when the max number of students enrolled by a course has been reached
 */
public class FullCourseException extends RuntimeException{
    public FullCourseException(String message) {
        super(message);
    }
}
