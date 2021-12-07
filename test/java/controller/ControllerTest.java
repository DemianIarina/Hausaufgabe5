package controller;

import model.Course;
import model.Pair;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import repository.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {
    Controller controller;
    Teacher t1;
    Teacher t2;
    Course c1;
    Course c2;
    Course c3;
    Student s1;
    Student s2;
    Student s3;
    StudentJDBCRepository studentJDBCRepository;
    TeacherJDBCRepository teacherJDBCRepository;
    CourseJDBCRepository courseJDBCRepository;
    static final String DB_URL = "jdbc:mysql://localhost:3306/lab5database";
    static final String USER = "root";
    static final String PASS = "lolipop";

    public static void resetDatabase(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM studenten_course;");
        stmt.executeUpdate("UPDATE student SET totalCredits = "+ 0 + ";");
        stmt.executeUpdate("DELETE FROM course;");
        stmt.executeUpdate("INSERT INTO course VALUES (1,'c1',1,2,10), (2,'c2',1,2,10), (3,'c3',2,2,11)");
    }

    @BeforeAll
    void init(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement()){

            resetDatabase(stmt);

            teacherJDBCRepository = new TeacherJDBCRepository(stmt);
            studentJDBCRepository = new StudentJDBCRepository(stmt);
            courseJDBCRepository = new CourseJDBCRepository(stmt);

            controller = new Controller(courseJDBCRepository,studentJDBCRepository,teacherJDBCRepository);
            t1 = teacherJDBCRepository.getAll().get(0);
            t2 = teacherJDBCRepository.getAll().get(1);

            c1 = this.courseJDBCRepository.getAll().get(0);
            c2 = this.courseJDBCRepository.getAll().get(1);
            c3  = this.courseJDBCRepository.getAll().get(2);

            s1 = this.studentJDBCRepository.getAll().get(0);
            s2 = this.studentJDBCRepository.getAll().get(1);
            s3 = this.studentJDBCRepository.getAll().get(2);

            controller.register(c1,s1);
            controller.register(c1,s2);

            controller.register(c2,s1);
            controller.register(c2,s3);

            controller.register(c3,s3);

        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
    }

    @Test
    void register() throws IOException, SQLException{

        //check the effect on the students
        List<Pair> expectedCourses = new ArrayList<>(Arrays.asList(new Pair(c1.getId(),c1.getCredits()),new Pair(c2.getId(),c2.getCredits())));
        assertEquals(expectedCourses,s1.getEnrolledCourses());

        //check the effect on the courses
        List<Integer> expectedStudents = new ArrayList<>(Arrays.asList(s1.getId(), s3.getId()));
        assertEquals(expectedStudents, c2.getStudentsEnrolledId());

        //when the student does not exist
        Student s4 = new Student(4,"Ionel", "Tau", 104);
        List<Pair> expectedCourses2 = new ArrayList<>(List.of());
        List<Integer> expectedStudents2 = new ArrayList<>(List.of(s3.getId()));
        try{
            controller.register(c3,s4);
        }
        catch (NonexistentArgumentException e){
            assertEquals(expectedCourses2,s4.getEnrolledCourses());
            assertEquals(expectedStudents2, c3.getStudentsEnrolledId());
        }

        //when the course does not exist
        Course c4  = new Course(4, "c4", 1, 2, 5);
        List<Integer> expectedStudents3 = new ArrayList<>(List.of());
        try{
            controller.register(c4,s1);
        }
        catch (NonexistentArgumentException e){
            assertEquals(expectedCourses,s1.getEnrolledCourses());
            assertEquals(expectedStudents3, c4.getStudentsEnrolledId());

        }
    }

    @Test
    void retriveCoursesWithFreePlaces() {
        List<Course> freePlacesCourses = controller.retriveCoursesWithFreePlaces();
        List<Course> expectedCourses = new ArrayList<>(List.of(c3));
        assertEquals(expectedCourses,freePlacesCourses);
    }

    @Test
    void retrieveStudentsEnrolledForACourse() {
        List<Integer> enrolledStudents = controller.retrieveStudentsEnrolledForACourse(c2);
        List<Integer> expectedStudents = new ArrayList<>(Arrays.asList(s1.getId(), s3.getId()));
        assertEquals(expectedStudents,enrolledStudents);
    }

    @Test
    void getAllCourses() {
        List<Course> allCourses = controller.getAllCourses();
        List<Course> expectedCourses = new ArrayList<>(Arrays.asList(c1, c2, c3));
        assertEquals(expectedCourses,allCourses);
    }

    @Test
    void sortStudents() {
        List<Student> obtainedStudents = controller.sortStudents();
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(s2,s3,s1));

        assertEquals(expectedStudents,obtainedStudents);
    }

    @Test
    void sortCourses() {
        List<Course> obtainedCourses = controller.sortCourses();
        List<Course> expectedCourses= new ArrayList<>(Arrays.asList(c1, c2, c3));

        assertEquals(expectedCourses,obtainedCourses);
    }

    @Test
    void filterStudents() {
        List<Student> obtainedStudents = controller.filterStudents();
        List<Student> expectedStudents = new ArrayList<>(List.of(s2));

        assertEquals(expectedStudents,obtainedStudents);
    }

    @Test
    void filterCourses() {
        List<Course> obtainedCourses = controller.filterCourses();
        List<Course> expectedCourses= new ArrayList<>(List.of(c3));

        assertEquals(expectedCourses,obtainedCourses);
    }
}
