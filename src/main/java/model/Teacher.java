package model;

import controller.AlreadyExistingException;
import controller.NonexistentArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * A specific type of person, who teaches at a Univeristy
 * He teaches one or more courses
 */
public class Teacher extends Person{
    private List<Course> courses;

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
        courses = new ArrayList<>();
    }

    public Teacher(){
        super();
    };

    /**
     * Adds a new curse to the list of taught courses
     * @param course a new Course object
     * @throws AlreadyExistingException if the course already exists
     */
    public void addCourse(Course course) throws AlreadyExistingException {
        if(courses.contains(course)){
            throw new AlreadyExistingException("Course already existing in the teachers list");
        }
        courses.add(course);
    }

    /**
     * Removes a course from the list of taught courses
     * @param course a course object
     * @throws NonexistentArgumentException if the course does not exist
     */
    public void removeCourse(Course course) throws NonexistentArgumentException {
        if(!courses.contains(course)){
            throw new NonexistentArgumentException("Course does not exist in the teachers list");
        }
        courses.remove(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}
