package controller;

import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

import java.io.IOException;
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
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;
    CourseRepository courseRepository;

    @BeforeEach
    void init() throws IOException {
        teacherRepository = new TeacherRepository();
        t1 = new Teacher("Ana", "Mare");
        t2 = new Teacher("Maria", "Pop");
        teacherRepository.create(t1);
        teacherRepository.create(t2);

        courseRepository = new CourseRepository();
        c1 = new Course("c1", t1,2, 10 );
        c2 = new Course("c2", t1, 2, 10);
        c3  = new Course("c3", t2, 2, 11);

        t1.addCourse(c1);
        t1.addCourse(c2);
        t2.addCourse(c3);

        courseRepository.create(c1);
        courseRepository.create(c2);
        courseRepository.create(c3);


        studentRepository = new StudentRepository();
        s1 = new Student("Mihai", "Dinero", 101);
        s2 = new Student("Bob", "Rus", 102);
        s3 = new Student("Bibi", "Pop", 103);
        studentRepository.create(s1);
        studentRepository.create(s2);
        studentRepository.create(s3);

        controller = new Controller(courseRepository,studentRepository,teacherRepository);

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

        //TODO test cand ai too many credits exception
    }

    @Test
    void retriveCoursesWithFreePlaces() {
        List<Course> freePlacesCourses = controller.retriveCoursesWithFreePlaces();
        List<Course> expectedCourses = new ArrayList<>(List.of(c3));
        assertEquals(expectedCourses,freePlacesCourses);
    }

    @Test
    void retrieveStudentsEnrolledForACourse() {
        List<Student> enrolledStudents = controller.retrieveStudentsEnrolledForACourse(c2);
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(s1, s3));
        assertEquals(expectedStudents,enrolledStudents);
    }

    @Test
    void getAllCourses() {
        List<Course> allCourses = controller.getAllCourses();
        List<Course> expectedCourses = new ArrayList<>(Arrays.asList(c1, c2, c3));
        assertEquals(expectedCourses,allCourses);
    }

    @Test
    void deleteCourse() throws IOException {
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
    void updateCreditsCourse() throws IOException {
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
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(s3,s2,s1));

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
        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(s2,s3));

        assertEquals(expectedStudents,obtainedStudents);
    }

    @Test
    void filterCourses() {
        List<Course> obtainedCourses = controller.filterCourses();
        List<Course> expectedCourses= new ArrayList<>(Arrays.asList(c1,c2,c3));

        assertEquals(expectedCourses,obtainedCourses);
    }
}