package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import controller.AlreadyExistingException;
import controller.NonexistentArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * A specific type of person, who teaches at a Univeristy
 * He teaches one or more courses
 */
//TODO maybe lista de cursuri sa fie numa u id uri
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class Teacher extends Person{
    private List<Integer> courses;

    public Teacher(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.courses = new ArrayList<>();
    }

    public Teacher(int id, String firstName, String lastName, List<Integer> courses) {
        super(id, firstName, lastName);
        this.courses = courses;
    }


    public Teacher(){
        super();
    }

    /**
     * Adds a new curse to the list of taught courses
     * @param courseId a new Course object
     * @throws AlreadyExistingException if the course already exists
     */
    public void addCourse(int courseId) throws AlreadyExistingException {
        if(courses.contains(courseId)){
            throw new AlreadyExistingException("Course already existing in the teachers list");
        }
        courses.add(courseId);
    }

    /**
     * Removes a course from the list of taught courses
     * @param course a course object
     * @throws NonexistentArgumentException if the course does not exist
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
