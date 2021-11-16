package controller;

import model.Student;

/**
 * Represents an exception triggered when the limit of credits has been reached
 */
public class TooManyCreditsException extends RuntimeException{

    private final long studentId;

    public TooManyCreditsException(String message, long studentId) {
        super(message);
        this.studentId = studentId;
    }

    public long getStudentId() {
        return studentId;
    }
}
