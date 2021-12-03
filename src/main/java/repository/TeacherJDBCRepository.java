package repository;

import model.Teacher;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TeacherJDBCRepository extends JDBCRepository<Teacher>{
    public TeacherJDBCRepository(List<Teacher> repoList) {
        super(repoList);
    }

    /**
     * returns the list from the file
     * @return a list of Teacher objects that were read
     */
    @Override
    public List<Teacher> read() {
        String selectSql = "SELECT * FROM employees";
        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getInt("id"));
                teacher.setFirstName(resultSet.getString("firstName"));
                teacher.setFirstName(resultSet.getString("firstName"));
                //TODO lista de cursuri
                teachers.add(teacher);
            }
            repoList = teachers;
        }
        return repoList;

    }

    /**
     * Adds a new Teacher to the repository
     * @param obj a new Object of type Teacher
     * @return the added object
     */
    @Override
    public Teacher create(Teacher obj) {
        return null;
    }

    /**
     * Modifies the list of courses of a specific teacher in the repository, found by firs and lastname
     * Modifies in the file
     * @param obj a teacher with the new list of courses
     * @return modified teacher
     */
    @Override
    public Teacher update(Teacher obj) {
        return null;
    }

    /**
     * delete an existing Teacher from the repo
     * @param obj the Teacher to be deleted
     */
    @Override
    public void delete(Teacher obj) {

    }
}
