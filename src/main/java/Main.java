import controller.Controller;
import model.Course;
import model.Student;
import model.Teacher;
import repository.*;
import view.KonsoleView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        StudentFileRepo studentRepository = new StudentFileRepo("Students.json");

        TeacherFileRepo teacherRepository = new TeacherFileRepo("Teachers.json");

        CourseFileRepo courseRepository = new CourseFileRepo("Courses.json");

        Controller controller = new Controller(courseRepository, studentRepository, teacherRepository);
        KonsoleView view = new KonsoleView(controller);
        view.main_menu();

    }

}

