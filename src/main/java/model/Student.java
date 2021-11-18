package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import controller.AlreadyExistingException;
import controller.NonexistentArgumentException;
import controller.TooManyCreditsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A specific type of person, enrolled in a University
 * He has a personal id, and a total number of credits from the courses he is enrolled
 * He can have maximum 30 credits
 * He can be enrolled in one or many courses
 */
public class Student extends Person implements Comparable<Student> {
    private long studentId;
    private int totalCredits;
    private List<Course> enrolledCourses;

    public Student(String firstName, String lastName, long studentId) {
        super(firstName, lastName);
        this.studentId = studentId;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
    }

    public Student(){super();}

    /**
     * Adds a new curse to the list of attended courses, and adds the corresponding number of credits to the total
     * @param course a new Course object
     * @throws TooManyCreditsException if the number of credits has been reached, the course list and credits number will not be upgraded
     * @throws AlreadyExistingException if the course already exists in the list
     */
    public void addCourse(Course course) throws TooManyCreditsException, AlreadyExistingException{
        if(enrolledCourses.contains(course)){
            throw new AlreadyExistingException("Course already existing in the student's list");
        }
        if(totalCredits+course.getCredits() <= 30){
            enrolledCourses.add(course);
            totalCredits = totalCredits+course.getCredits();
        }
        else {
            throw new TooManyCreditsException("The credits limit has been reached for " + this.studentId, this.getStudentId());
        }

    }

    /**
     * Removes a course from the list of attended courses, and decreases the total number of credits, with the according value
     * @param course a course object
     * @throws NonexistentArgumentException if the course does not exist in the list
     */
    public void removeCourse(Course course) throws NonexistentArgumentException {
        if(!enrolledCourses.contains(course)){
            throw new NonexistentArgumentException("Course does not exist in the student's list");
        }

        //update the number of credits of the student
        totalCredits = totalCredits - course.getCredits();
        enrolledCourses.remove(course);
    }

    /**
     * Change the total number of credits upon the change of the number of credits of a course
     * If through the change, more than 30 credits are reached, the course will be removed from the students list, and the according value of credits will be decreased from the total
     * @param course a course object
     * @param newCredits the new value for the credits
     * @throws NonexistentArgumentException if the course does not exist in the list
     * @throws TooManyCreditsException if the maximum value of credits will be reached
     */
    public void updateCredits(Course course, int newCredits) throws NonexistentArgumentException, TooManyCreditsException{
        if(!enrolledCourses.contains(course)){
            throw new NonexistentArgumentException("Course does not exist in the student's list");
        }

        for(Course actualCourse: enrolledCourses) {
            //update the nr of credits of the student -> 2 possibilities (remains under 30 de credits, or not)
            if (Objects.equals(actualCourse.getName(), course.getName())) {
                int newValue = totalCredits - actualCourse.getCredits() + newCredits;

                if (newValue <= 30) {
                    totalCredits = newValue;
                    break;
                } else {
                    //remove the course from the students list + throw to
                    enrolledCourses.remove(actualCourse);
                    totalCredits = totalCredits - actualCourse.getCredits();
                    throw new TooManyCreditsException("The credits limit has been reached for " + this.studentId + ". Student unenrolled!", this.studentId);
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

    /**
     * Function to compare two instances of Student, according to firstName
     * @param student2 another object of type Student
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Student student2) {
        return this.getFirstName().compareTo(student2.getFirstName());
    }
}
