package repository;

import model.Course;
import model.Teacher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeacherJDBCRepository extends JDBCRepository<Teacher>{
    public TeacherJDBCRepository(Connection conn) throws SQLException {
        super(conn);
    }

    /**
     * returns the list from the database
     * @return a list of Teacher objects that were read
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public List<Teacher> read() throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT * FROM teacher left join course c on teacher.id = c.idTeacher";

            try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                List<Teacher> teachers = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("teacher.id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    int courseId = resultSet.getInt("c.id");    //converts NULL to 0

                    if (courseId > 0) {    //when the current teacher has courses
                        if (teachers.stream().anyMatch(teacher -> teacher.getId() == id)) {      //when the current teacher has already other courses
                            Teacher searchedTeacher = teachers.stream()
                                    .filter(teacher -> teacher.getId() == id)
                                    .findAny()
                                    .orElse(null);
                            assert searchedTeacher != null;
                            searchedTeacher.addCourse(courseId);           //the teacher already exists in the list
                        } else {
                            Teacher teacher = new Teacher(id, firstName, lastName);           //create the new teacher
                            teacher.addCourse(courseId);
                            teachers.add(teacher);
                        }
                    } else {
                        Teacher teacher = new Teacher(id, firstName, lastName);    //create teacher without courses
                        teachers.add(teacher);
                    }
                }
                repoList = teachers;
            }
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return repoList;

    }

    /**
     * Adds a new Teacher, WITHOUT COURSES, to the repository
     * @param obj a new Object of type Teacher
     * @return the added object
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public Teacher create(Teacher obj) throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            repoList.add(obj);
            stmt.executeUpdate("INSERT INTO teacher VALUES (" + obj.getId()
                    + ", \'" + obj.getFirstName() + "\', \'" + obj.getLastName() + "\');");
        }  catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return obj;
    }

    /**
     * Modifies the list of courses of a specific teacher in the repository
     * Does NOT remove the course from the courses database
     * @param obj a teacher with the new list of courses
     * @return modified teacher
     */
    @Override
    public Teacher update(Teacher obj) {
        return obj;   //the object is already modified
    }

    /**
     * delete an existing Teacher from the repo
     * Modifies obly the teaxher table
     * @param obj the Teacher to be deleted
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public void delete(Teacher obj) throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            repoList.remove(obj);
            stmt.executeUpdate("DELETE FROM teacher where id = " + obj.getId() + ";");
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
    }
}