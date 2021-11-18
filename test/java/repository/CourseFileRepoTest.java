package repository;

import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseFileRepoTest {
    CourseFileRepo courseFileRepo;

    @BeforeEach
    void init() throws IOException {
        courseFileRepo = new CourseFileRepo("Courses.json");
    }

    @Test
    void writeToFile() throws IOException {
        Teacher t1 = new Teacher("Ana", "Mare");
        Teacher t2 = new Teacher("Maria", "Pop");

        Course c1 = new Course("c1", t1,2, 10 );
        Course c2 = new Course("c2", t1, 2, 10);
        Course c3  = new Course("c3", t2, 2, 11);

        List<Course> courses = new ArrayList<>(List.of(c1,c2, c3));

        courseFileRepo.writeToFile(courses);
    }

    @Test
    void readFromFile() {
        System.out.println(courseFileRepo.getAll());
    }

    @Test
    void update() throws IOException {
        Teacher t1 = new Teacher("Ana", "Mare");
        Course c3  = new Course("c2", t1, 2, 11);

        Student s1 = new Student("Mihai", "Dinero", 101);
        Student s2 = new Student("Bob", "Rus", 102);

        List<Student> students = new ArrayList<>(List.of(s1,s2));
        c3.setStudentsEnrolled(students);

        courseFileRepo.update(c3);
    }

    @Test
    void updateCredits() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

}