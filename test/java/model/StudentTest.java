package model;

import controller.TooManyCreditsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student student;
    private Course course;
    private Course course2;

    @BeforeEach
    void init(){
        Teacher t1 = new Teacher(1,"Ana", "Mare");
        course = new Course(1,"c1", 1,2, 10 );
        t1.addCourse(course.getId());

        course2 = new Course(2,"c2", 1, 2, 15 );
        t1.addCourse(course2.getId());

        student = new Student(1,"Mihai", "Dinero", 101);
        student.addCourse(course);
    }

    @Test
    void addCourse() {
        student.addCourse(course2);
        List<Pair> expectedCourses = new ArrayList<>(Arrays.asList(new Pair(course.getId(),course.getCredits()), new Pair(course2.getId(),course2.getCredits())));
        assertEquals(expectedCourses,student.getEnrolledCourses());
        assertEquals(25,student.getTotalCredits());
    }

    @Test
    void removeCourse() {
        student.addCourse(course2);
        student.removeCourse(course);
        List<Pair> expectedCourses = new ArrayList<>(List.of(new Pair(course2.getId(), course2.getCredits())));
        assertEquals(expectedCourses, student.getEnrolledCourses());
        assertEquals(15, student.getTotalCredits());
    }

    @Test
    void updateCredits() {
        //when the total credits remains under 30
        student.addCourse(course2);
        student.updateCredits(course,15);
        assertEquals(30,student.getTotalCredits());

        //when the total credits go over 30
        try {
            student.updateCredits(course2,17);
        }
        catch (TooManyCreditsException e){
            List<Pair> expectedCourses = new ArrayList<>(List.of(new Pair(course.getId(), 15)));
            assertEquals(expectedCourses, student.getEnrolledCourses());
            assertEquals(15,student.getTotalCredits());
        }


    }
}