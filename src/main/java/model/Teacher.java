package model;

import controller.AlreadyExistingException;
import controller.NonexistentArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * A specific type of person, who teaches at a University
 * He teaches one or more courses
 */
public class Teacher extends Person{
    private List<Integer> courses;

    public Teacher(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.courses = new ArrayList<>();
    }


    /**
     * Adds a new curse id to the list of taught courses
     * @param courseId a new Course id
     * @throws AlreadyExistingException if the course already exists
     */
    public void addCourse(int courseId) throws AlreadyExistingException {
        if(courses.contains(courseId)){
            throw new AlreadyExistingException("Course already existing in the teachers list");
        }
        courses.add(courseId);
    }

    /**
     * Removes a course id from the list of taught courses
     * @param course a course id
     * @throws NonexistentArgumentException if the course does not exist in the teachers list
     */
    public void removeCourse(int course) throws NonexistentArgumentException {
        if(!courses.contains(course)){
            throw new NonexistentArgumentException("Course does not exist in the teachers list");
        }
        courses.remove(Integer.valueOf(course));
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void setCourses(List<Integer> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId()  +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", courses=" + courses +
                '}';
    }
}
