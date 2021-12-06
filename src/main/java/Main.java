//import controller.Controller;
import controller.Controller;
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
import java.util.List;

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

            System.out.println(teacherJDBCRepository.getAll());
            System.out.println(studentJDBCRepository.getAll());
            System.out.println(courseJDBCRepository.getAll());


            Controller controller = new Controller(courseJDBCRepository,studentJDBCRepository,teacherJDBCRepository);

/*            System.out.println(controller.retriveCoursesWithFreePlaces());
            System.out.println(controller.getAllCourses());
            System.out.println(controller.sortStudents());
            System.out.println(controller.sortCourses());
            System.out.println(controller.filterStudents());
            System.out.println(controller.filterCourses());*/


            //register
            Course c1 = courseJDBCRepository.getAll().get(0);
            Course c2 = courseJDBCRepository.getAll().get(1);
            Course c3  = courseJDBCRepository.getAll().get(2);
            Student s1 = studentJDBCRepository.getAll().get(0);
            Student s2 = studentJDBCRepository.getAll().get(1);
            Student s3 = studentJDBCRepository.getAll().get(2);
            controller.register(c1,s1);
            controller.register(c1,s2);
            controller.register(c2,s1);
            controller.register(c2,s3);
            controller.register(c3,s3);

            System.out.println(controller.retriveCoursesWithFreePlaces());
            System.out.println(controller.retrieveStudentsEnrolledForACourse(c2));

            controller.updateCreditsCourse(c2,20);


            System.out.println(studentJDBCRepository.getAll());
            System.out.println(courseJDBCRepository.getAll());

            /*controller.deleteCourse(c3);

            System.out.println(studentJDBCRepository.getAll());
            System.out.println(courseJDBCRepository.getAll());*/


            /*Teacher t1 = new Teacher(3,"Lala", "Haa");
            //teacherJDBCRepository.create(t1);

            //add in db cursu
            Course c1 = new Course(4,"c3", 3, 2,12);
            t1.addCourse(c1.getId());
            t1.removeCourse(c1.getId());
            teacherJDBCRepository.update(t1);


            teacherJDBCRepository.delete(t1);
            System.out.println(teacherJDBCRepository.read());*/


            /*Student s1 = new Student(4,"Ionela", "Fira", 104);
            Course c1 = new Course(4,"c3", 3, 2,12);
            studentJDBCRepository.create(s1);

            s1.addCourse(c1);
            studentJDBCRepository.update(s1);
            System.out.println(s1.getTotalCredits());
            System.out.println(s1.getEnrolledCourses());

            c1.setCredits(13);
            studentJDBCRepository.update(s1);
            System.out.println(s1.getTotalCredits());
            System.out.println(s1.getEnrolledCourses());

            s1.removeCourse(c1);
            System.out.println(s1.getTotalCredits())\
            System.out.println(s1.getEnrolledCourses());


            studentJDBCRepository.delete(s1);


            Course c1 = new Course(4,"c4", 3, 2,12);
            courseJDBCRepository.create(c1);
            System.out.println(courseJDBCRepository.getAll());

            //System.out.println(teacherJDBCRepository.read());    //TODO in controller, dupa ce adaug ceva sa mai citesc o data baza de date
            //fiindca nu arata bine System.out.println(teacherJDBCRepository.getAll()); fara readu

            c1.setCredits(15);
            courseJDBCRepository.updateCredits(c1);

            c1.addStudent(1);
            courseJDBCRepository.update(c1);

            courseJDBCRepository.delete(c1);
            System.out.println(courseJDBCRepository.getAll());*/

        } catch (SQLException exeption) {
            exeption.printStackTrace();
        }


    }

}

