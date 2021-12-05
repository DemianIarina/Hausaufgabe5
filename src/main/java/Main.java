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

            /*Teacher t1 = new Teacher(3,"Lala", "Haa");
            //teacherJDBCRepository.create(t1);

            //add in db cursu
            Course c1 = new Course(4,"c3", 3, 2,12);
            t1.addCourse(c1.getId());
            t1.removeCourse(c1.getId());
            teacherJDBCRepository.update(t1);


            teacherJDBCRepository.delete(t1);
            System.out.println(teacherJDBCRepository.read());*/


           Student s1 = new Student(4,"Ionela", "Fira", 104);
           studentJDBCRepository.create(s1);
           studentJDBCRepository.delete(s1);

        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }


    }

}

