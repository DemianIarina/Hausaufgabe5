package repository;

import model.Course;
import model.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherFileRepoTest {
    TeacherFileRepo teacherFileRepo;

    @BeforeEach
    void init() throws IOException {
        teacherFileRepo = new TeacherFileRepo("Teachers.json");
    }

    @Test
    void readFromFile() {
        System.out.println(teacherFileRepo.getAll());
    }

    //TODO: intra in bucla infnita
    @Test
    void update() throws IOException {
        List<Teacher> teachers = teacherFileRepo.getAll();
        teachers.forEach(System.out::println);

        teachers.add(teachers.get(0));

        teacherFileRepo.writeToFile(teachers);
    }
}