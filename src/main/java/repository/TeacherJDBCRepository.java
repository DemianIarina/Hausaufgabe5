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
    public TeacherJDBCRepository(Statement stmt) {
        super(stmt);
    }

    /**
     * returns the list from the file
     * @return a list of Teacher objects that were read
     */
    @Override
    public List<Teacher> read() throws SQLException {
        String selectSql = "SELECT * FROM teacher left join course c on teacher.id = c.idTeacher";

        try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
            List<Teacher> teachers = new ArrayList<>();
            while (resultSet.next()) {
                int id= resultSet.getInt("teacher.id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int courseId = resultSet.getInt("c.id");    //converts NULL to 0

                if(courseId>0) {    //when the current teacher has courses
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
                }
                else{
                    Teacher teacher = new Teacher(id, firstName, lastName);    //create teacher without courses
                    teachers.add(teacher);
                }
            }
            repoList = teachers;
        }
        return repoList;

    }

    /**
     * Adds a new Teacher, WITHOUT COURSES, to the repository
     * @param obj a new Object of type Teacher
     * @return the added object
     */
    @Override
    public Teacher create(Teacher obj) throws SQLException {
        repoList.add(obj);
        stmt.executeUpdate("INSERT INTO teacher VALUES ("+ obj.getId()
                + ", \'" + obj.getFirstName() + "\', \'" + obj.getLastName() + "\');");
        return obj;
    }

    /**
     * Modifies the list of courses of a specific teacher in the repository, found by id
     * Modifies the database also
     * @param obj a teacher with the new list of courses
     * @return modified teacher
     */
    @Override
    public Teacher update(Teacher obj) throws SQLException {
        Teacher teacherToUpdate = this.repoList.stream()
                .filter(teacher -> teacher.getId()== obj.getId())
                .findFirst()
                .orElseThrow();

        ResultSet rs = stmt.executeQuery("SELECT id FROM course WHERE idTeacher = " + obj.getId() + ";");
        List<Integer> oldCourses = new ArrayList<>();
        while(rs.next()) {
           oldCourses.add(rs.getInt("id"));      //find the actual list of courses from the database
        }

        //find the deleted course
        List<Integer> aux = new ArrayList<>(oldCourses);
        aux.removeAll(obj.getCourses());        //the one which is in the database
        int deletedCourseId = aux.get(0);       // but in the given teacher object not
        stmt.executeUpdate("DELETE FROM course WHERE id = " + deletedCourseId +";");

        teacherToUpdate.setCourses(obj.getCourses());
        return teacherToUpdate;
    }

    /**
     * delete an existing Teacher from the repo
     * @param obj the Teacher to be deleted
     */
    @Override
    public void delete(Teacher obj) throws SQLException {
        repoList.remove(obj);
        stmt.executeUpdate("DELETE FROM teacher where id = "+ obj.getId()+";");
    }
}
