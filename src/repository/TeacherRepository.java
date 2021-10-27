package repository;

import model.Teacher;

public class TeacherRepository extends InMemoryRepository<Teacher> {

    public TeacherRepository() {
        super();
    }

    @Override
    public Teacher update(Teacher obj) {   //man braucht die
        return null;
    }
}
