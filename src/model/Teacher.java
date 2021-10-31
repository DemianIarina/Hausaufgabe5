package model;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person{
    private List<Course> courses;

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
        courses = new ArrayList<Course>();
    }

    public void addCourse(Course course){
        courses.add(course);
    }

    public void removeCourse(Course course){
        courses.remove(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


}
