package repository;

import model.Teacher;

import java.util.Objects;

/**
 *  Represents an in memory repository for teachers
 */
public class TeacherRepository extends InMemoryRepository<Teacher> {

    public TeacherRepository() {
        super();
    }

    /**
     * Modifies the list of courses of a specific teacher in the repository, found by firs and lastname
     * @param obj a teacher with the new list of courses
     * @return modified teacher
     */
    @Override
    public Teacher update(Teacher obj) {    //used by updating the number of credits of a course
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(teacher -> Objects.equals(teacher.getFirstName(), obj.getFirstName()) && Objects.equals(teacher.getLastName(), obj.getLastName()))
                .findFirst()
                .orElseThrow();

        teacherToUpdate.setCourses(obj.getCourses());

        return teacherToUpdate;
    }
}
