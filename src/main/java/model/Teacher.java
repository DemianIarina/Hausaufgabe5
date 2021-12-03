package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int idPerson;
    private List<Course> courses;

    public Teacher(int idPerson, String firstName, String lastName) {
        super(firstName, lastName);
        this.idPerson = idPerson;
        this.courses = new ArrayList<>();
    }

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
        this.courses = new ArrayList<>();
    }

    public Teacher(){
        super();
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

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
