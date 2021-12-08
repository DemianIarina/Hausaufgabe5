package repository;

import model.Course;
import model.Pair;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentJDBCRepositoryTest {
    StudentJDBCRepository studentJDBCRepository;
    Student s1;
    Student s2;
    Student s3;
    static final String DB_URL = "jdbc:mysql://localhost:3306/lab5database";
    static final String USER = "root";
    static final String PASS = "password";

    public static void resetDatabase(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM student WHERE id NOT IN (1,2,3);");
    }

    @BeforeEach
    void init(){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            resetDatabase(stmt);

            studentJDBCRepository = new StudentJDBCRepository(conn);

            s1 = this.studentJDBCRepository.getAll().get(0);
            s2 = this.studentJDBCRepository.getAll().get(1);
            s3 = this.studentJDBCRepository.getAll().get(2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void create() throws SQLException {
        Student s4 = new Student(4,"Ionela", "Fira", 104);
        studentJDBCRepository.create(s4);
        List<Student> expepectedStudents = new ArrayList<>(List.of(s1,s2,s3,s4));

        assertEquals(expepectedStudents,studentJDBCRepository.getAll());
    }

    @Test
    void delete() throws SQLException {
        Student s4 = new Student(4,"Ionela", "Fira", 104);
        studentJDBCRepository.create(s4);
        Course c1 = new Course(2,"c2", 1,2, 10 );
        Pair coursePair = new Pair(c1.getId(), c1.getCredits());
        s4.setEnrolledCourses(List.of(coursePair));

        studentJDBCRepository.delete(s4);
        List<Student> expepectedStudents = new ArrayList<>(List.of(s1,s2,s3));

        assertEquals(expepectedStudents,studentJDBCRepository.getAll());
    }
}