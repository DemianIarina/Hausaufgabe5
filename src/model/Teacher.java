package model;

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

    /**
     * Adds a new curse to the list of taught courses
     * @param course a new Course object
     * @throws IllegalArgumentException if the course already exists
     */
    public void addCourse(Course course) throws IllegalArgumentException{
        if(courses.contains(course)){
            throw new IllegalArgumentException("Course already existing in the teachers list");
        }
        courses.add(course);
    }

    /**
     * Removes a course from the list of taught courses
     * @param course a course object
     * @throws IllegalArgumentException if the course does not exist
     */
    public void removeCourse(Course course) throws IllegalArgumentException{
        if(!courses.contains(course)){
            throw new IllegalArgumentException("Course does not exist in the teachers list");
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
