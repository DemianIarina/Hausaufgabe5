//package model;
//
//import controller.AlreadyExistingException;
//import controller.FullCourseException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CourseTest {
//    private Student student;
//    private Student student2;
//    private Student student3;
//    private Course course;
//
//
//    @BeforeEach
//    void init() {
//        Teacher t1 = new Teacher("Ana", "Mare");
//        course = new Course("c1", t1, 2, 10);
//        t1.addCourse(course);
//
//        student = new Student("Mihai", "Dinero", 101);
//        student2 = new Student("Matei", "Rus", 102);
//        student3 = new Student("Mihaela", "Pop", 103);
//
//    }
//
//    @Test
//    void addStudent() {
//        course.addStudent(student);
//        course.addStudent(student2);
//        List<Student> expectedStudents = new ArrayList<>(Arrays.asList(student,student2));
//        assertEquals(expectedStudents, course.getStudentsEnrolled());
//
//        //adding a already existing student
//        try{
//            course.addStudent(student);
//        }
//        catch (AlreadyExistingException e){
//            assertEquals(expectedStudents, course.getStudentsEnrolled());
//        }
//
//        //the maxEnrollement has been reached
//        try{
//            course.addStudent(student3);
//        }
//        catch (FullCourseException e){
//            assertEquals(expectedStudents, course.getStudentsEnrolled());
//            assertEquals(2,course.getStudentsEnrolled().size());
//        }
//
//    }
//}