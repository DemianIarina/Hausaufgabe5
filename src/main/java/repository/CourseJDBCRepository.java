package repository;

import model.Course;
import model.Pair;
import model.Student;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseJDBCRepository extends JDBCRepository<Course>{
    public CourseJDBCRepository(Statement stmt) {
        super(stmt);
    }

    /**
     * returns the list from the file
     * @return a list of Courses objects that were read
     */
    @Override
    public List<Course> read() throws SQLException {
        String selectSql = "SELECT * FROM course inner join studenten_course sc " +
                "on course.id = sc.idCourse ";
        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("course.id");
                String name = resultSet.getString("name");
                int idTeacher= resultSet.getInt("idTeacher");
                int maxEnrollment = resultSet.getInt("maxEnrollment");
                int credits= resultSet.getInt("credits");
                int studentId = resultSet.getInt("idStudent");

                if (courses.stream().anyMatch(course -> course.getId() == id)) {
                    Course searchedcourse = courses.stream()
                            .filter(course -> course.getId() == id)
                            .findAny()
                            .orElse(null);
                    assert searchedcourse != null;
                    searchedcourse.addStudent(studentId);
                }
                else{
                Course course = new Course(id, name, idTeacher,maxEnrollment,credits);
                course.addStudent(studentId);
                courses.add(course);
                }
            }
            repoList = courses;
        }
        return repoList;

    }

    /**
     * Adds a new Course to the repository
     * @param obj a new Object of type Course
     * @return the added object
     */
    @Override
    public Course create(Course obj) throws SQLException {
        repoList.add(obj);
        stmt.executeUpdate("INSERT INTO course VALUES ("+ obj.getId()
                + ", \'" + obj.getName() + "\', " + obj.getIdTeacher() + "," + obj.getMaxEnrollment()+ ", " + obj.getCredits() + ");");
        return obj;
    }

    /**
     * Modifies the students list of a course in repository
     * Does NOT update the studenten_course database
     * @param obj a course with the new list of students
     * @return modified course
     */
    @Override
    public Course update(Course obj) throws SQLException {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> course.getId() == obj.getId())
                .findFirst()
                .orElseThrow();
        courseToUpdate.setStudentsEnrolledId(obj.getStudentsEnrolledId());

        return courseToUpdate;
    }

    /**
     * Modified the number of credits of a specific course in the repository, found by id
     * Modifies in the database also
     * @param obj a course with a new value for credits
     * @return modified course
     */
    public Course updateCredits(Course obj) throws SQLException {
        Course courseToUpdate = this.repoList.stream()
                .filter(course -> course.getId() == obj.getId())
                .findFirst()
                .orElseThrow();
        courseToUpdate.setCredits(obj.getCredits());

        stmt.executeUpdate("UPDATE course SET credits = "+ obj.getCredits() + " where id = " + obj.getId() +";");

        return courseToUpdate;
    }

    /**
     * delete an existing Course from the repo
     * @param obj the Course to be deleted
     */
    @Override
    public void delete(Course obj) throws SQLException {
        repoList.remove(obj);
        stmt.executeUpdate("DELETE FROM course where id = "+ obj.getId()+";");

    }
}
