package repository;

import model.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the repository of Courses objects, which is connected to the database
 * Every modification is projected on the database
 */
public class CourseJDBCRepository extends JDBCRepository<Course>{
    public CourseJDBCRepository(Connection conn) throws SQLException {
        super(conn);
    }

    /**
     * returns the list of courses from the database
     * @return a list of Courses objects that were read
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public List<Course> read() throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            String selectSql = "SELECT * FROM course left join studenten_course sc " +
                    "on course.id = sc.idCourse ";
            try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
                List<Course> courses = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("course.id");
                    String name = resultSet.getString("name");
                    int idTeacher = resultSet.getInt("idTeacher");
                    int maxEnrollment = resultSet.getInt("maxEnrollment");
                    int credits = resultSet.getInt("credits");
                    int studentId = resultSet.getInt("idStudent");

                    if (courses.stream().anyMatch(course -> course.getId() == id)) {   //if the course already in the list is
                        Course searchedcourse = courses.stream()                        //means that the course has more than 1 students enrolled
                                .filter(course -> course.getId() == id)
                                .findAny()
                                .orElse(null);
                        assert searchedcourse != null;
                        searchedcourse.addStudent(studentId);
                    } else {
                        Course course = new Course(id, name, idTeacher, maxEnrollment, credits);
                        if (studentId != 0) {
                            course.addStudent(studentId);           //when the course has a student enrolled
                        }
                        courses.add(course);
                    }
                }
                repoList = courses;
            }
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return repoList;

    }

    /**
     * Adds a new Course to the repository,
     * incl. in the database
     * @param obj a new Object of type Course
     * @return the added object
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public Course create(Course obj) throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            repoList.add(obj);
            stmt.executeUpdate("INSERT INTO course VALUES (" + obj.getId()
                    + ", \'" + obj.getName() + "\', " + obj.getIdTeacher() + "," + obj.getMaxEnrollment() + ", " + obj.getCredits() + ");");
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return obj;
    }

    /**
     * Modifies the students list of a course in repository
     * Modifies studenten_course table also
     * @param obj a course with the new list of students
     * @return modified course
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public Course update(Course obj) throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            List<Integer> oldStudents = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("SELECT idStudent FROM studenten_course  WHERE idCourse = " + obj.getId() + ";");
            while (rs.next()) {
                oldStudents.add(rs.getInt("idStudent"));
            }

            if (oldStudents.size() > obj.getStudentsEnrolledId().size()) {   //when a student has been deleted
                List<Integer> aux = new ArrayList<>(oldStudents);
                aux.removeAll(obj.getStudentsEnrolledId());        //the ones which are in the database
                // but in the given student object not
                for (int deletedStudentId : aux) {
                    oldStudents.remove(Integer.valueOf(deletedStudentId));

                    stmt.executeUpdate("DELETE FROM studenten_course WHERE idCourse = " + obj.getId() + " AND idStudent = " + deletedStudentId + ";");
                }

            } else if (oldStudents.size() < obj.getStudentsEnrolledId().size()) {    //when a student has been added
                List<Integer> aux = new ArrayList<>(obj.getStudentsEnrolledId());
                aux.removeAll(oldStudents);
                int addedStudentId = aux.get(0);
                oldStudents.add(addedStudentId);

                stmt.executeUpdate("INSERT INTO studenten_course VALUES(" + addedStudentId + ", " + obj.getId() + ");");
            }
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return obj;
    }

    /**
     * Modified the number of credits of a specific course in the repository, found by id
     * Modifies in the course table also
     * @param obj a course with a new value for credits
     * @return modified course
     * @throws SQLException when any exception regarding the SQL happen
     */
    public Course updateCredits(Course obj) throws SQLException {
        try( Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("UPDATE course SET credits = " + obj.getCredits() + " where id = " + obj.getId() + ";");
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        return obj;
    }

    /**
     * delete an existing Course from the repo
     * Modifies in the database Course table and in the studenten_course link table
     * @param obj the Course to be deleted
     * @throws SQLException when any exception regarding the SQL happen
     */
    @Override
    public void delete(Course obj) throws SQLException {
        repoList.remove(obj);
        try( Statement stmt = conn.createStatement() ) {
            stmt.executeUpdate("DELETE FROM studenten_course WHERE idCourse = " + obj.getId() + ";");   //delete from the link table
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
        try( Statement stmt2 = conn.createStatement(); ) {
            repoList.remove(obj);

            stmt2.executeUpdate("DELETE FROM course where id = " + obj.getId() + ";");
        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }
    }
}