import controller.Controller;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;
import view.KonsoleView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TeacherRepository teacherRepository = new TeacherRepository();
        Teacher t1 = new Teacher("Ana", "Mare");
        Teacher t2 = new Teacher("Maria", "Pop");
        teacherRepository.create(t1);
        teacherRepository.create(t2);

        CourseRepository courseRepository = new CourseRepository();
        Course c1 = new Course("c1", t1, 2, 10);
        Course c2 = new Course("c2", t1, 2, 10);
        Course c3 = new Course("c3", t2, 2, 11);

        t1.addCourse(c1);
        t1.addCourse(c2);
        t2.addCourse(c3);

        courseRepository.create(c1);
        courseRepository.create(c2);
        courseRepository.create(c3);


        StudentRepository studentRepository = new StudentRepository();
        Student s1 = new Student("Mihai", "Dinero", 101);
        Student s2 = new Student("Batei", "Rus", 102);
        Student s3 = new Student("Bihaela", "Pop", 103);
        studentRepository.create(s1);
        studentRepository.create(s2);
        studentRepository.create(s3);

        Controller controller = new Controller(courseRepository, studentRepository, teacherRepository);
        KonsoleView view = new KonsoleView(controller);
        view.main_menu();

    }

}

