package repository;

import model.Student;

import java.io.IOException;
import java.util.List;

public class StudentJDBCRepository extends JDBCRepository<Student> {

    public StudentJDBCRepository(List<Student> repoList) {
        super(repoList);
    }

    /**
     * returns the list from the database
     * @return a list of Students objects that were read
     */
    @Override
    public List<Student> read() {
        return null;
    }

    /**
     * Adds a new Student to the repository
     * @param obj a new Object of type Student
     * @return the added object
     */
    @Override
    public Student create(Student obj) {
        return null;
    }

    /**
     * Modifies the courses list and the number of credits from a student in the repository, found by id
     * Modifies in the database also
     * @param obj a student with the new courses and number of credits
     * @return modified student
     */
    @Override
    public Student update(Student obj) {
        return null;
    }

    /**
     * delete an existing Student from the repo
     * @param obj the Student to be deleted
     */
    @Override
    public void delete(Student obj) {

    }
}
