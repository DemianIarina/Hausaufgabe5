package repository;

import model.Course;

import java.io.IOException;
import java.util.List;

public class CourseJDBCRepository extends JDBCRepository<Course>{
    public CourseJDBCRepository(List<Course> repoList) {
        super(repoList);
    }

    /**
     * returns the list from the file
     * @return a list of Courses objects that were read
     */
    @Override
    public List<Course> read() {
        return null;
    }

    /**
     * Adds a new Course to the repository
     * @param obj a new Object of type Course
     * @return the added object
     */
    @Override
    public Course create(Course obj) {
        return null;
    }

    /**
     * Modifies the students list of a course in repository
     * Modifies in the file also
     * @param obj a course with the new list of students
     * @return modified course
     */
    @Override
    public Course update(Course obj) {
        return null;
    }

    /**
     * Modified the number of credits of a specific course in the repository, found by name
     * Modifies in the file also
     * @param obj a course with a new value for credits
     * @return modified course
     */
    public Course updateCredits(Course obj){
        return null;
    }

    /**
     * delete an existing Course from the repo
     * @param obj the Course to be deleted
     */
    @Override
    public void delete(Course obj) {

    }
}
