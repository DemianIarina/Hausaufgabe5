package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Student;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StudentFileRepo extends FileRepository<Student> {
    public StudentFileRepo(String fileName) {
        super(fileName);
    }

    /**
     * returns the list from the file
     * @return a list of Students objects that were read
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    @Override
    public List<Student> readFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return Arrays.asList(objectMapper.readValue(new File("data.json"), Student[].class));
    }


    /**
     * Modifies the courses list and the number of credits from a student in the repository, found by id
     * Modifies in the file also
     * @param obj a student with the new courses and number of credits
     * @return modified student
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    @Override
    public Student update(Student obj) throws IOException {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentId() == obj.getStudentId())
                .findFirst()
                .orElseThrow();

        studentToUpdate.setEnrolledCourses(obj.getEnrolledCourses());
        studentToUpdate.setTotalCredits(obj.getTotalCredits());

        writeToFile(repoList);

        return studentToUpdate;
    }
}
