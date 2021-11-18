package repository;

import model.Course;
import model.Student;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class StudentFileRepoTest {
    StudentFileRepo studentFileRepo;

    @BeforeEach
    void init() throws IOException {
        studentFileRepo = new StudentFileRepo("Students.json");
    }

    @Test
    void writeToFile() throws IOException {
        Student s1 = new Student("Mihai", "Dinero", 101);
        Student s2 = new Student("Bob", "Rus", 102);
        Student s3 = new Student("Bibi", "Pop", 103);

        List<Student> students = new ArrayList<>(List.of(s1,s2,s3));

        studentFileRepo.writeToFile(students);
    }

    @Test
    void readFromFile() {
        System.out.println(studentFileRepo.getAll());
    }


    @Test
    void update() throws IOException {
        List<Student> students = studentFileRepo.getAll();
        students.forEach(System.out::println);
        Teacher t1 = new Teacher("Ana", "Mare");
        Course c1 = new Course("c2", t1,2, 10 );
        t1.setCourses(List.of(c1));
        Student s1 = new Student("Mihaela", "Pop",103);

        s1.setEnrolledCourses(List.of(c1));

        studentFileRepo.update(s1);
    }

    @Test
    void create() throws IOException {
        Student s1 = new Student("Ion", "Goga", 104);
        studentFileRepo.create(s1);
        System.out.println(studentFileRepo.getAll());
    }

    @Test
    void delete() throws IOException {

        Student s2 = studentFileRepo.getAll().get(2);
        List<Student> students = studentFileRepo.getAll();
        students.forEach(System.out::println);

        studentFileRepo.delete(s2);
        System.out.println("-----------");
        students.forEach(System.out::println);
    }
}