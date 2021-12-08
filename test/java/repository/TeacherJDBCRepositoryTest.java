package repository;

import model.Course;
import model.Teacher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeacherJDBCRepositoryTest {
    TeacherJDBCRepository teacherJDBCRepository;
    Teacher t1;
    Teacher t2;
    static final String DB_URL = "jdbc:mysql://localhost:3306/lab5database";
    static final String USER = "root";
    static final String PASS = "lolipop";

    public static void resetDatabase(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM teacher WHERE id NOT IN (1,2);");
    }

    @BeforeEach
    void init(){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            resetDatabase(stmt);

            teacherJDBCRepository = new TeacherJDBCRepository(conn);

            t1 = teacherJDBCRepository.getAll().get(0);
            t2 = teacherJDBCRepository.getAll().get(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void create() throws SQLException {
        Teacher t3 = new Teacher(3,"Lala", "Haa");
        teacherJDBCRepository.create(t3);
        List<Teacher> expectedTeachers = new ArrayList<>(List.of(t1,t2,t3));
        assertEquals(expectedTeachers,teacherJDBCRepository.getAll());

    }

    @Test
    void delete() throws SQLException {
        Teacher t3 = new Teacher(3,"Lala", "Haa");
        teacherJDBCRepository.create(t3);
        t3.addCourse(4);
        t3.addCourse(5);
        teacherJDBCRepository.delete(t3);

        List<Teacher> expectedTeachers = new ArrayList<>(List.of(t1, t2));
        assertEquals(expectedTeachers, teacherJDBCRepository.getAll());
    }
}
