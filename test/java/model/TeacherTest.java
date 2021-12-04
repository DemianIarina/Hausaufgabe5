//package model;
//
//import controller.AlreadyExistingException;
//import controller.NonexistentArgumentException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TeacherTest {
//    private Teacher teacher;
//    private Course course;
//    private Course course2;
//
//    @BeforeEach
//    void init() {
//        teacher = new Teacher("Ana", "Mare");
//        course = new Course("c1", teacher, 2, 10);
//        course2 = new Course("c2", teacher, 2, 15);
//        teacher.addCourse(course);
//    }
//
//    @Test
//    void addCourse() {
//        teacher.addCourse(course2);
//        List<Course> expectedCourses = new ArrayList<>(Arrays.asList(course,course2));
//        assertEquals(expectedCourses,teacher.getCourses());
//
//        //when adding an already existing course
//        try{
//            teacher.addCourse(course);
//        }
//        catch (AlreadyExistingException e){
//            assertEquals(expectedCourses, teacher.getCourses());
//        }
//    }
//
//    @Test
//    void removeCourse() {
//        teacher.addCourse(course2);
//        teacher.removeCourse(course);
//        List<Course> expectedCourses = new ArrayList<>(List.of(course2));
//        assertEquals(expectedCourses,teacher.getCourses());
//
//        //when removing a not existing course from a teachers list
//        try{
//            teacher.removeCourse(course);
//        }
//        catch (NonexistentArgumentException e){
//            assertEquals(expectedCourses, teacher.getCourses());
//        }
//
//    }
//}