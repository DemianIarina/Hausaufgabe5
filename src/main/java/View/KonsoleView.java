package View;

import controller.Controller;

import java.util.Scanner;

public class KonsoleView {
    public Controller controller;

    public KonsoleView(Controller controller) {
        this.controller = controller;
    }

    public void main_menu(){
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
            reader.close();

            if (inp == 1)
                ;

		    else
                if (inp == 11) {
                    System.out.println("Quiting...");
                    break;
                }
                else {  //wenn den input war keine den gegebene Optionen, wird den menu neu angezeigt
                    System.out.println("Input is invalid \n Enter again:");
                    main_menu();
                }
        }
    }

}
