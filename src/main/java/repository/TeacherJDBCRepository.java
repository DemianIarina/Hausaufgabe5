package repository;

import model.Teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherJDBCRepository extends JDBCRepository<Teacher>{
    public TeacherJDBCRepository(Statement stmt) {
        super(stmt);
    }

    /**
     * returns the list from the file
     * @return a list of Teacher objects that were read
     */
    @Override
    public List<Teacher> read() throws SQLException {
        String selectSql = "SELECT * FROM teacher";
        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()) {
                int id= resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Teacher teacher = new Teacher(id, firstName,lastName);
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
