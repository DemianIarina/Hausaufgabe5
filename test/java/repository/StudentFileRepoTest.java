package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class StudentFileRepoTest {
    StudentFileRepo studentFileRepo;

    @BeforeEach
    void init() throws IOException {
        studentFileRepo = new StudentFileRepo("Students.json");
    }

    @Test
    void readFromFile() {
        System.out.println(studentFileRepo.getAll());
    }
}