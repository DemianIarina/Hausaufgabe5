package repository;

import model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseJDBCRepositoryTest {
    Course c1;
    Course c2;
    Course c3;
    CourseJDBCRepository courseJDBCRepository;
    static final String DB_URL = "jdbc:mysql://localhost:3306/lab5database";
    static final String USER = "root";
    static final String PASS = "lolipop";

    public static void resetDatabase(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM studenten_course;");
        stmt.executeUpdate("DELETE FROM course WHERE id NOT IN (1,2,3);");
    }

    @BeforeEach
    void init(){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            resetDatabase(stmt);

            courseJDBCRepository = new CourseJDBCRepository(conn);

            c1 = courseJDBCRepository.getAll().get(0);
            c2 = courseJDBCRepository.getAll().get(1);
            c3 = courseJDBCRepository.getAll().get(2);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void create() throws SQLException {
        Course c4  = new Course(4,"c4", 1, 2, 13);
        courseJDBCRepository.create(c4);
        List<Course> expectedCourses = new ArrayList<>(List.of(c1,c2,c3,c4));

        assertEquals(expectedCourses,courseJDBCRepository.getAll());
    }

    @Test
    void update() throws SQLException {
        Course c4  = new Course(4,"c4", 1, 2, 13);
        courseJDBCRepository.create(c4);
        c4.addStudent(1);

        //when adding a student
        courseJDBCRepository.update(c4);
        List<Integer> expectedStudents =  new ArrayList<>(List.of(1));
        assertEquals(expectedStudents, courseJDBCRepository.getAll().get(3).getStudentsEnrolledId());

        c4.addStudent(2);
        courseJDBCRepository.update(c4);

        //deleting more students
        c4.removeStudent(1);
        c4.removeStudent(2);
        courseJDBCRepository.update(c4);
        List<Integer> expectedStudents2 =  new ArrayList<>(List.of());
        assertEquals(expectedStudents2, courseJDBCRepository.getAll().get(3).getStudentsEnrolledId());

    }

    @Test
    void delete() throws SQLException {
        Course c4  = new Course(4,"c4", 1, 2, 13);
        courseJDBCRepository.create(c4);
        c4.addStudent(1);
        courseJDBCRepository.update(c4);

        courseJDBCRepository.delete(c4);
        List<Course> expectedCourses = new ArrayList<>(List.of(c1,c2,c3));
        assertEquals(expectedCourses,courseJDBCRepository.getAll());
    }
}