/*
package controller;

import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    StudentJDBCRepository studentRepository;
    TeacherJDBCRepository teacherRepository;
    CourseJDBCRepository courseRepository;

    @BeforeEach
    void init() throws Exception {
        CopyFromFileaToFileb.copyContent("StudentsOG.json", "Students.json");
        CopyFromFileaToFileb.copyContent("TeachersOG.json", "Teachers.json");
        CopyFromFileaToFileb.copyContent("CoursesOG.json", "Courses.json");

        studentRepository = new StudentJDBCRepository("Students.json");
        teacherRepository = new TeacherFileRepo("Teachers.json");
        courseRepository = new CourseFileRepo("Courses.json");
        controller = new Controller(courseRepository, studentRepository, teacherRepository);

        t1 = teacherRepository.getAll().get(0);
        t2 = teacherRepository.getAll().get(1);

        c1 = courseRepository.getAll().get(0);
        c2 = courseRepository.getAll().get(1);
        c3  = courseRepository.getAll().get(2);

        s1 = studentRepository.getAll().get(0);
        s2 = studentRepository.getAll().get(1);
        s3 = studentRepository.getAll().get(2);

        controller.register(c1,s1);
        controller.register(c1,s2);

        controller.register(c2,s1);
        controller.register(c2,s3);

        controller.register(c3,s3);
    }

    @Test
    void register() throws IOException{

        //check the effect on the students
        List<Course> expectedCourses = new ArrayList<>(Arrays.asList(c1,c2));
        assertEquals(expectedCourses,s1.getEnrolledCourses());

        //check the effect on the courses
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(s1, s3));
        assertEquals(expectedStudents, c2.getStudentsEnrolled());

        //when the student does not exist
        Student s4 = new Student("Ionel", "Tau", 104);
        List<Course> expectedCourses2 = new ArrayList<>(List.of());
        List<Student> expectedStudents2 = new ArrayList<>(List.of(s3));
        try{
            controller.register(c3,s4);
        }
        catch (NonexistentArgumentException e){
            assertEquals(expectedCourses2,s4.getEnrolledCourses());
            assertEquals(expectedStudents2, c3.getStudentsEnrolled());
        }

        //when the course does not exist
        Course c4  = new Course("c4", t1, 2, 5);
        List<Student> expectedStudents3 = new ArrayList<>(List.of());
        try{
            controller.register(c4,s1);
        }
        catch (NonexistentArgumentException e){
            assertEquals(expectedCourses,s1.getEnrolledCourses());
            assertEquals(expectedStudents3, c4.getStudentsEnrolled());

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
        List<Integer> expectedStudents = new ArrayList<>(Arrays.asList(s1, s3));
        assertEquals(expectedStudents,enrolledStudents);
    }

    @Test
    void getAllCourses() {
        List<Course> allCourses = controller.getAllCourses();
        List<Course> expectedCourses = new ArrayList<>(Arrays.asList(c1, c2, c3));
        assertEquals(expectedCourses,allCourses);
    }

    @Test
    void deleteCourse() throws SQLException {
        controller.deleteCourse(c3);
        List<Course> expectedCoursesTeacher = new ArrayList<>(List.of());
        assertEquals(expectedCoursesTeacher,t2.getCourses());

        List<Course> expectedCoursesStudent = new ArrayList<>(List.of(c2));
        assertEquals(expectedCoursesStudent,s3.getEnrolledCourses());

        List<Course> expectedCourses = new ArrayList<>(Arrays.asList(c1, c2));
        assertEquals(expectedCourses, courseRepository.getAll());

        //when the course does not exist
        Course c4  = new Course("c4", t1, 2, 5);
        List<Course> expectedCourses2 = new ArrayList<>(Arrays.asList(c1, c2));
        try{
            controller.deleteCourse(c4);
        }
        catch (NonexistentArgumentException e){
            assertEquals(expectedCourses2,courseRepository.getAll());
        }
    }

    @Test
    void updateCreditsCourse() throws IOException, SQLException {
        controller.updateCreditsCourse(c2,19);
        assertEquals(29, s1.getTotalCredits());
        assertEquals(30, s3.getTotalCredits());
        assertEquals(19, c2.getCredits());

        //when the course does not exist
        Course c4  = new Course("c4", t1, 2, 5);
        try{
            controller.updateCreditsCourse(c4,20);
        }
        catch (NonexistentArgumentException e){
            assertEquals(5, c4.getCredits());
        }

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
}*/
