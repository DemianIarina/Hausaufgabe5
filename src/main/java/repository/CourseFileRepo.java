package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Course;
import model.Teacher;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CourseFileRepo extends FileRepository<Course> {
    public CourseFileRepo(String fileName) throws IOException {
        super(fileName);
    }

    /**
     * returns the list from the file
     * @return a list of Courses objects that were read
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    @Override
    public List<Course> readFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        return Arrays.asList(objectMapper.readValue(new File(fileName), Course[].class));

    }

    /**
     * Modifies the students list of a course in repository
     * Modifies in the file also
     * @param obj a course with the new list of students
     * @return modified course
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    @Override
    public Course update(Course obj) throws IOException {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()) && course.getTeacher() == obj.getTeacher())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setStudentsEnrolled(obj.getStudentsEnrolled());

        writeToFile(repoList);

        return courseToUpdate;
    }

    /**
     * Modified the number of credits of a specific course in the repository, found by name
     * Modifies in the file also
     * @param obj a course with a new value for credits
     * @return modified course
     * @throws IOException if there occurs an error with the ObjectOutputStream
     */
    public Course updateCredits(Course obj) throws IOException {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> Objects.equals(course.getName(), obj.getName()) && course.getTeacher() == obj.getTeacher())
                .findFirst()
                .orElseThrow();

        courseToUpdate.setCredits(obj.getCredits());

        writeToFile(repoList);

        return courseToUpdate;
    }
}
