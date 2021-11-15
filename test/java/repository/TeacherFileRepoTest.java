package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

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
}