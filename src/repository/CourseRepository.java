package repository;

import model.Course;

import java.util.Objects;

public class CourseRepository extends InMemoryRepository<Course> {
    public CourseRepository() {
        super();
    }

    @Override
    public Course update(Course obj) {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()) && course.getTeacher() == obj.getTeacher())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setStudentsEnrolled(obj.getStudentsEnrolled());

        return courseToUpdate;
    }
}
