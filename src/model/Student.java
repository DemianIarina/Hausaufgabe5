package model;

import controller.TooManyCreditsException;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person{
    private long studentId;
    private int totalCredits;
    private List<Course> enrolledCourses;

    public Student(String firstName, String lastName, long studentId) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<Course>();;
    }

    public void addCourse(Course course) throws TooManyCreditsException{
        if(totalCredits+course.getCredits() <= 30){
            enrolledCourses.add(course);
            totalCredits = totalCredits+course.getCredits();
        }
        else {
            throw new TooManyCreditsException("The credits limit has been reached for " + this);
        }

    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstname=" + getFirstName() +
                ", lastname=" + getLastName() +
                ", studentId=" + studentId +
                ", totalCredits=" + totalCredits +
                '}';
    }
}
