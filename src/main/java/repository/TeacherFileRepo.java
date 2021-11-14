package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Student;
import model.Teacher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TeacherFileRepo extends FileRepository<Teacher> {
    public TeacherFileRepo(String fileName) {
        super(fileName);
    }


    /**
     * returns the list from the file
     * @return a list of Teacher objects that were read
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    @Override
    public List<Teacher> readFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return Arrays.asList(objectMapper.readValue(new File(fileName), Teacher[].class));
    }


    /**
     * Modifies the list of courses of a specific teacher in the repository, found by firs and lastname
     * Modifies in the file
     * @param obj a teacher with the new list of courses
     * @return modified teacher
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    @Override
    public Teacher update(Teacher obj) throws IOException {
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(teacher -> Objects.equals(teacher.getFirstName(), obj.getFirstName()) && Objects.equals(teacher.getLastName(), obj.getLastName()))
                .findFirst()
                .orElseThrow();

        teacherToUpdate.setCourses(obj.getCourses());

        writeToFile(repoList);

        return teacherToUpdate;
    }
}
