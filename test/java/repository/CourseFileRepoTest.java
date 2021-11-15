package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CourseFileRepoTest {
    CourseFileRepo courseFileRepo;

    @BeforeEach
    void init() throws IOException {
        courseFileRepo = new CourseFileRepo("Courses.json");
    }

    @Test
    void readFromFile() {
        System.out.println(courseFileRepo.getAll());
    }
}