//package repository;
//
//import model.Course;
//import model.Teacher;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TeacherFileRepoTest {
//    TeacherFileRepo teacherFileRepo;
//
//    @BeforeEach
//    void init() throws IOException {
//        teacherFileRepo = new TeacherFileRepo("Teachers.json");
//    }
//
//    @Test
//    void writeToFile() throws IOException {
//        Teacher t1 = new Teacher("Ana", "Mare");
//        Teacher t2 = new Teacher("Maria", "Pop");
//
//        List<Teacher> teachers = new ArrayList<>(List.of(t1,t2));
//
//        teacherFileRepo.writeToFile(teachers);
//    }
//
//
//    @Test
//    void readFromFile() {
//        System.out.println(teacherFileRepo.getAll());
//    }
//
//    @Test
//    void update() throws IOException {
//        List<Teacher> teachers = teacherFileRepo.getAll();
//        teachers.forEach(System.out::println);
//        Teacher t1 = new Teacher("Ana", "Mare");
//        Course c1 = new Course("c2", t1,2, 10 );
//        t1.setCourses(List.of(c1));
//
//        teacherFileRepo.update(t1);
//    }
//
//    @Test
//    void create() throws IOException {
//
//        Teacher t2 = new Teacher("AnaMaria", "Mara");
//        teacherFileRepo.create(t2);
//    }
//
//    @Test
//    void delete() throws IOException {
//
//        Teacher t2 = teacherFileRepo.getAll().get(2);
//        //teacherFileRepo.create(t2);
//        List<Teacher> teachers = teacherFileRepo.getAll();
//        teachers.forEach(System.out::println);
//
//        teacherFileRepo.delete(t2);
//        System.out.println("-----------");
//        teachers.forEach(System.out::println);
//    }
//
//
//}