import controller.RegistrationSystem;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

public class Main {

    public static void main(String[] args) {
        TeacherRepository teacherRepository = new TeacherRepository();
        Teacher t1 = new Teacher("Ana", "Mare");
        Teacher t2 = new Teacher("Maria", "Pop");
        teacherRepository.create(t1);
        teacherRepository.create(t2);

        CourseRepository courseRepository = new CourseRepository();
        Course c1 = new Course("c1", t1,2, 10 );
        Course c2 = new Course("c2", t1, 2, 10);
        Course c3  = new Course("c3", t2, 2, 11);
        courseRepository.create(c1);
        courseRepository.create(c2);
        courseRepository.create(c3);


        StudentRepository studentRepository = new StudentRepository();
        Student s1 = new Student("Mihai", "Dinu", 101);
        studentRepository.create(s1);

        RegistrationSystem rs = new RegistrationSystem(courseRepository,studentRepository,teacherRepository);
        rs.register(c1,s1);
        rs.register(c2,s1);


        System.out.println(s1.getEnrolledCourses());
        System.out.println(s1.getTotalCredits());


    }
}
