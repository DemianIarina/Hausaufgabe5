package repository;

import model.Course;

import java.util.Objects;

/**
 * Represents an in memory repository for courses
 */
public class CourseRepository extends InMemoryRepository<Course> {
    public CourseRepository() {
        super();
    }

    /**
     * Modifies the students list of a course in repository
     * @param obj a course with the new list of students
     * @return modified course
     */
    @Override
    public Course update(Course obj) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()) && course.getTeacher() == obj.getTeacher())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setStudentsEnrolled(obj.getStudentsEnrolled());

        return courseToUpdate;
    }

    /**
     * Modified the number of credits of a specific course in the repository, found by name
     * @param obj a course with a new value for credits
     * @return modified course
     */
    public Course updateCredits(Course obj) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()) && course.getTeacher() == obj.getTeacher())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setCredits(obj.getCredits());
        return courseToUpdate;
    }

    /**
     * Removes the course from the repository
     * @param obj the course to be deleted
     */
    @Override
    public void delete(Course obj) {
        super.delete(obj);
    }
}
