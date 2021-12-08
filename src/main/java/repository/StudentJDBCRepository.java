package repository;

import model.Course;
import model.Pair;
import model.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the repository of Students objects, which is connected to the database
 * Every modification is projected on the database
 */
public class StudentJDBCRepository extends JDBCRepository<Student> {

    public StudentJDBCRepository(Connection conn) throws SQLException {
        super(conn);
    }

    /**
     * returns the list from the database
     * @return a list of Students objects that were read
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public List<Student> read() throws SQLException {
        try( Statement stmt = conn.createStatement()){
            String selectSql = "SELECT * FROM student left join studenten_course sc " +
                    "on student.id = sc.idStudent " +
                    "left join course c " +
                    "on sc.idCourse = c.id";
            try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                List<Student> students = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("student.id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    long studentId = resultSet.getLong("studentId");
                    int totalCredits = resultSet.getInt("totalCredits");
                    int courseId = resultSet.getInt("idCourse");
                    String name = resultSet.getString("name");
                    int idTeacher = resultSet.getInt("idTeacher");
                    int maxEnrollment = resultSet.getInt("maxEnrollment");
                    int credits = resultSet.getInt("credits");

                    if (students.stream().anyMatch(student -> student.getId() == id)) {
                        Student searchedStudent = students.stream()
                                .filter(student -> student.getId() == id)
                                .findAny()
                                .orElse(null);
                        Course newCourse = new Course(courseId, name, idTeacher, maxEnrollment, credits);
                        assert searchedStudent != null;
                        searchedStudent.getEnrolledCourses().add(new Pair(newCourse.getId(), newCourse.getCredits()));

                    }
                    else{
                        Student student = new Student(id, firstName, lastName, studentId, totalCredits);
                        if(courseId != 0){
                            Course newCourse = new Course(courseId, name, idTeacher, maxEnrollment, credits);
                            student.getEnrolledCourses().add(new Pair(newCourse.getId(), newCourse.getCredits()));
                        }
                        students.add(student);
                    }
                }
                repoList = students;
            }
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return repoList;
    }

    /**
     * Adds a new Student to the repository, WITH THE DEFAULT NR OF CREDITS
     * @param obj a new Object of type Student
     * @return the added object
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public Student create(Student obj) throws SQLException {
        try( Statement stmt = conn.createStatement()){
            repoList.add(obj);
            stmt.executeUpdate("INSERT INTO student VALUES ("+ obj.getId()
                    + ", \'" + obj.getFirstName() + "\', \'" + obj.getLastName() + "\' ," + obj.getStudentId()+ ", DEFAULT);");
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return obj;
    }

    /**
     * Modifies the courses list and the number of credits from a student in the repository, found by id
     * Modifies the student table, in the credits column
     * Does NOT modify the studenten_course table
     * @param obj a student with the new courses and number of credits
     * @return modified student
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public Student update(Student obj) throws SQLException {
        try( Statement stmt = conn.createStatement()){
            //update the total of credits in the database
            stmt.executeUpdate("UPDATE student SET totalCredits = "+ obj.getTotalCredits() + " where id = " + obj.getId() +";");  //total credits of given object
            // because it has the modified nr of credits
        }
        catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return obj;
    }

    /**
     * delete an existing Student from the repo
     * Modifies the student table
     * @param obj the Student to be deleted
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public void delete(Student obj) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            repoList.remove(obj);
            stmt.executeUpdate("DELETE FROM student where id = " + obj.getId() + ";");
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
    }
}