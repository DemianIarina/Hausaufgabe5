import controller.RegistrationSystem;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<Course> coursesT1 = new ArrayList<>(Arrays.asList(c1,c2));
        List<Course> coursesT2 = new ArrayList<>(Arrays.asList(c3));

        t1.setCourses(coursesT1);
        t2.setCourses(coursesT2);

        courseRepository.create(c1);
        courseRepository.create(c2);
        courseRepository.create(c3);


        StudentRepository studentRepository = new StudentRepository();
        Student s1 = new Student("Mihai", "Dinu", 101);
        Student s2 = new Student("Matei", "Rus", 102);
        Student s3 = new Student("Mihaela", "Pop", 103);
        studentRepository.create(s1);
        studentRepository.create(s2);
        studentRepository.create(s3);

        RegistrationSystem rs = new RegistrationSystem(courseRepository,studentRepository,teacherRepository);
        rs.register(c1,s1);
        rs.register(c1,s2);

        rs.register(c2,s1);
        rs.register(c2,s3);



        rs.register(c3,s3);



        System.out.println(s1.getTotalCredits());
        System.out.println(rs.retriveCoursesWithFreePlaces());
        System.out.println(rs.retrieveStudentsEnrolledForACourse(c2));
        System.out.println(rs.getAllCourses());

       //delete


    }
}
