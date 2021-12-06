package model;

import controller.AlreadyExistingException;
import controller.FullCourseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Student student;
    private Student student2;
    private Student student3;
    private Course course;


    @BeforeEach
    void init() {
        Teacher t1 = new Teacher(1,"Ana", "Mare");
        course = new Course(1,"c1", 1, 2, 10);
        t1.addCourse(course.getId());

        student = new Student(1,"Mihai", "Dinero", 101);
        student2 = new Student(2,"Matei", "Rus", 102);
        student3 = new Student(3,"Mihaela", "Pop", 103);

    }

    @Test
    void addStudent() {
        course.addStudent(student.getId());
        course.addStudent(student2.getId());
        List<Integer> expectedStudents = new ArrayList<>(Arrays.asList(student.getId(),student2.getId()));
        assertEquals(expectedStudents, course.getStudentsEnrolledId());

        //adding a already existing student
        try{
            course.addStudent(student.getId());
        }
        catch (AlreadyExistingException e){
            assertEquals(expectedStudents, course.getStudentsEnrolledId());
        }

        //the maxEnrollement has been reached
        try{
            course.addStudent(student3.getId());
        }
        catch (FullCourseException e){
            assertEquals(expectedStudents, course.getStudentsEnrolledId());
            assertEquals(2,course.getStudentsEnrolledId().size());
        }

    }
}