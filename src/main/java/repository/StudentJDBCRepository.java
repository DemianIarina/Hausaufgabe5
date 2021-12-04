package repository;

import model.Student;
import model.Teacher;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentJDBCRepository extends JDBCRepository<Student> {

    public StudentJDBCRepository(Statement stmt) {
        super(stmt);
    }

    /**
     * returns the list from the database
     * @return a list of Students objects that were read
     */
    @Override
    public List<Student> read() throws SQLException {
        String selectSql = "SELECT * FROM student";
        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                int id= resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                long studentId = resultSet.getLong("studentId");
                int totalCredits= resultSet.getInt("totalCredits");
                Student student = new Student(id, firstName,lastName,studentId,totalCredits);
                //TODO lista de cursuri
                students.add(student);
            }
            repoList = students;
        }
        return repoList;

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
