package repository;

import model.Teacher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeAll
    void init() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); Statement stmt = conn.createStatement()) {

            Teacher t1 = new Teacher(1,"Ana", "Mare");

            teacherJDBCRepository = mock(TeacherJDBCRepository.class);

            teacherJDBCRepository.create(t1);
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
    void update() {
    }

    @Test
    void delete() {
    }
}