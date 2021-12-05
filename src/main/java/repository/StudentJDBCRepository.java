package repository;

import com.mysql.cj.conf.ConnectionUrlParser;
import model.Course;
import model.Pair;
import model.Student;
import model.Teacher;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentJDBCRepository extends JDBCRepository<Student> {

    public StudentJDBCRepository(Statement stmt) {
        super(stmt);
    }

    /**
     * returns the list from the database
     * @return a list of Students objects that were read
     */
    @Override
    public List<Student> read() throws SQLException {
        String selectSql = "SELECT * FROM student inner join studenten_course sc " +
                "on student.id = sc.idStudent " +
                "inner join course c " +
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
                    searchedStudent.addCourse(newCourse);

                }
                else{
                    Student student = new Student(id, firstName, lastName, studentId, totalCredits);
                    Course newCourse = new Course(courseId, name, idTeacher, maxEnrollment, credits);
                    student.addCourse(newCourse);
                    students.add(student);
                }
            }
            repoList = students;
        }
        return repoList;
    }

    /**
     * Adds a new Student to the repository, WITH THE DEFAULT NR OF CREDITS
     * @param obj a new Object of type Student
     * @return the added object
     */
    @Override
    public Student create(Student obj) throws SQLException {
        repoList.add(obj);
        stmt.executeUpdate("INSERT INTO student VALUES ("+ obj.getId()
                + ", \'" + obj.getFirstName() + "\', \'" + obj.getLastName() + "\' ," + obj.getStudentId()+ ", DEFAULT);");
        return obj;
    }

    /**
     * Modifies the courses list and the number of credits from a student in the repository, found by id
     * Modifies the studenten_course database also
     * @param obj a student with the new courses and number of credits
     * @return modified student
     */
    //TODO peste tot unde imi arunca exceptii sa pun in javadocs
    //TODO maybe face rezl prost la update creditsCourse din controller!!!!!!!!!!!!
    @Override
    public Student update(Student obj) throws SQLException {
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentId() == obj.getStudentId())
                .findFirst()
                .orElseThrow();


        List<Integer> oldCourses = new ArrayList<>();
        for(Pair elem: studentToUpdate.getEnrolledCourses()) {
            oldCourses.add(elem.getCourseId());      //find the old courses ids
        }

        if(oldCourses.size() > obj.getEnrolledCourses().size()){    //when a course has been deleted
            //find the deleted course
            List<Integer> aux = new ArrayList<>(oldCourses);
            List<Integer> newEnrolledCoursesId = new ArrayList<>();
            for(Pair elem : obj.getEnrolledCourses()){
                newEnrolledCoursesId.add(elem.getCourseId());    //from the pairs, get only the id
            }
            aux.removeAll(newEnrolledCoursesId);        //the one which is in the database
            int deletedCourseId = aux.get(0);        // but in the given student object not

            Pair deletedCoursePair = obj.getEnrolledCourses().stream()
                    .filter(elem -> elem.getCourseId() == deletedCourseId)
                    .findFirst()
                    .orElseThrow();

            Course deletedCourse = new Course(deletedCoursePair.getCourseId(), deletedCoursePair.getCredits());
            studentToUpdate.removeCourse(deletedCourse);
            stmt.executeUpdate("DELETE FROM studenten_course WHERE idStudent = " +studentToUpdate.getId() + "AND idCourse = " + deletedCourseId +";");
        }
        else
            if(oldCourses.size() < obj.getEnrolledCourses().size()){    //when a course has been added
                List<Integer> newEnrolledCoursesId = new ArrayList<>();
                for(Pair elem : obj.getEnrolledCourses()){
                    newEnrolledCoursesId.add(elem.getCourseId());    //from the pairs, get only the id
                }

                List<Integer> aux = new ArrayList<>(newEnrolledCoursesId);
                aux.removeAll(oldCourses);
                int addedCourseId = aux.get(0);

                Pair addedCoursePair = obj.getEnrolledCourses().stream()
                        .filter(elem -> elem.getCourseId() == addedCourseId)
                        .findFirst()
                        .orElseThrow();

                Course deletedCourse = new Course(addedCoursePair.getCourseId(), addedCoursePair.getCredits());
                studentToUpdate.addCourse(deletedCourse);

                stmt.executeUpdate("INSERT INTO studenten_course VALUES(" +studentToUpdate.getId() + ", " + addedCourseId +");");
            }

        //update the total of credits in the database
        stmt.executeUpdate("UPDATE student SET totalCredits = "+ studentToUpdate.getTotalCredits() + " where id = " + studentToUpdate.getId() +";");


        return studentToUpdate;
    }

    /**
     * delete an existing Student from the repo
     * @param obj the Student to be deleted
     */
    @Override
    public void delete(Student obj) throws SQLException {
        repoList.remove(obj);
        stmt.executeUpdate("DELETE FROM student where id = "+ obj.getId()+";");
    }
}
