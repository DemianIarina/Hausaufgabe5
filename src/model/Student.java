package model;

import controller.TooManyCreditsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            //TODO in UI catch: the user must input another course
            throw new TooManyCreditsException("The credits limit has been reached for " + this);
        }

    }

    public void removeCouse(Course course){
        //update the number of credits of the student
        totalCredits = totalCredits - course.getCredits();
        enrolledCourses.remove(course);

    }
    
    public void updateCredits(Course course, int newCredits){
        for(Course actualCourse: enrolledCourses) {
            //update the nr of credits of the student -> 2 possibilities (ramane sub 30 de credite, or not)
            if (Objects.equals(actualCourse.getName(), course.getName())) {
                int newValue = totalCredits - actualCourse.getCredits() + newCredits;

                if (newValue <= 30) {
                    totalCredits = newValue;
                    break;
                } else {
                    //remove the course from the students list + throw to
                    enrolledCourses.remove(actualCourse);
                    totalCredits = totalCredits - actualCourse.getCredits();
                    throw new TooManyCreditsException("The credits limit has been reached for " + this.studentId + ".\n Course has been deleted!");

                }
            }
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
