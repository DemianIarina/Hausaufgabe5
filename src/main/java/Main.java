import controller.RegistrationSystem;
import model.Course;
import model.Student;
import model.Teacher;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while(true){
            System.out.println("-----------------\n" +
                    "Menu\n" +
                    "1. Register Student to Course\n" +
                    "2. Retrive Courses With Free Places\n" +
                    "3. Retrieve Students Enrolled For A Course\n" +
                    "4. Get All Courses\n" +
                    "5. Delete Course\n" +
                    "6. Update Credits Course\n" +
                    "7. Sort Students by First Name\n" +
                    "8. Sort Courses by Name\n" +
                    "9. Filter Students\n" +
                    "10. FilterCourses\n" +
                    "11. Exit\n" +
                    "------------------------");

            Scanner reader = new Scanner(System.in);
            System.out.println("Choose a option (write the number):");
            int inp = reader.nextInt();

            if (inp == 1)
                System.out.println("1 pressed");
            else {
                if (inp == 11) {
                    System.out.println("Quiting...");
                    break;
                } else {  //wenn den input war keine den gegebene Optionen, wird den menu neu angezeigt
                    System.out.println("Input is invalid \nEnter again:");
                    main(args);
                }
            }
        }
    }

}

