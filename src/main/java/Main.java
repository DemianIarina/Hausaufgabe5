//import controller.Controller;
import model.Course;
import model.Student;
import model.Teacher;
import repository.*;
//import view.KonsoleView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    static final String DB_URL = "jdbc:mysql://localhost:3306/lab5database";
    static final String USER = "root";
    static final String PASS = "lolipop";

    public static void main(String[] args) throws IOException {
//        StudentFileRepo studentRepository = new StudentFileRepo("Students.json");
//
//        TeacherFileRepo teacherRepository = new TeacherFileRepo("Teachers.json");
//
//        CourseFileRepo courseRepository = new CourseFileRepo("Courses.json");
//
//        Controller controller = new Controller(courseRepository, studentRepository, teacherRepository);
//        KonsoleView view = new KonsoleView(controller);
//        view.main_menu();

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();){
            TeacherJDBCRepository teacherJDBCRepository = new TeacherJDBCRepository(stmt);
            StudentJDBCRepository studentJDBCRepository = new StudentJDBCRepository(stmt);
            CourseJDBCRepository courseJDBCRepository = new CourseJDBCRepository(stmt);
            System.out.println(teacherJDBCRepository.read());
            System.out.println(studentJDBCRepository.read());
            System.out.println(courseJDBCRepository.read());

        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }


    }

}

