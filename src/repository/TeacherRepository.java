package repository;

import model.Teacher;

public class TeacherRepository extends InMemoryRepository<Teacher> {

    public TeacherRepository() {
        super();
    }

    @Override
    public Teacher update(Teacher obj) {    //used by updating the number of credits of a course
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(teacher -> teacher.getFirstName() == obj.getFirstName() && teacher.getLastName() == obj.getLastName())
                .findFirst()
                .orElseThrow();

        teacherToUpdate.setCourses(obj.getCourses());

        return teacherToUpdate;
    }
}
