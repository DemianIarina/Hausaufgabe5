package view;

import controller.*;
import model.Course;
import model.Student;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class KonsoleView {

    public Controller controller;

    public KonsoleView(Controller controller) {
        this.controller = controller;
    }

    public void main_menu() throws SQLException {
        Scanner reader = new Scanner(System.in);

        loop:
        while (true) {
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

            System.out.println("Choose a option (write the number):");
            try {
                int inp = reader.nextInt();
                reader.nextLine();

                switch (inp) {
                    case 1:
                        try {
                            System.out.println("Enter Course Name:");
                            String courseName = reader.nextLine();
                            Course course = controller.getCourses().getAll().stream()
                                    .filter(actualCourse -> courseName.equals(actualCourse.getName()))
                                    .findAny()
                                    .orElseThrow(() -> new NonexistentArgumentException("No course found with the name: " + courseName));

                            System.out.println("Enter Student Id:");
                            long studentId = reader.nextLong();
                            Student student = controller.getStudents().getAll().stream()
                                    .filter(actualStudent -> studentId == actualStudent.getStudentId())
                                    .findAny()
                                    .orElseThrow(() -> new NonexistentArgumentException("No student found with the id: " + studentId));
                            try {
                                controller.register(course, student);
                            } catch (NonexistentArgumentException e) {
                                System.out.println("Invalid arguments, try again");
                            } catch (AlreadyExistingException | FullCourseException | TooManyCreditsException e2) {
                                System.out.println("Could not register, error occurred while registering: " + e2);
                            } catch (SQLException e3) {
                                System.out.println("Database failed, try again");
                                e3.printStackTrace();
                            }
                            break;
                        } catch (NonexistentArgumentException e) {
                            System.out.println("Invalid arguments, try again");
                        }
                        break;

                    case 2:
                        List<Course> coursesWithFreePlaces = controller.retriveCoursesWithFreePlaces();
                        System.out.println(coursesWithFreePlaces);
                        break;

                    case 3:
                        System.out.println("Enter Course Name:");
                        String courseName2 = reader.nextLine();

                        try {
                            Course course2 = controller.getCourses().getAll().stream()
                                    .filter(actualCourse -> courseName2.equals(actualCourse.getName()))
                                    .findAny()
                                    .orElseThrow(() -> new NonexistentArgumentException("No course found with the name: " + courseName2));
                            try {
                                List<Integer> studentList = controller.retrieveStudentsEnrolledForACourse(course2);
                                System.out.println(studentList);
                            } catch (NonexistentArgumentException e) {
                                System.out.println("Invalid arguments, try again");
                            }
                            break;
                        } catch (NonexistentArgumentException e) {
                            System.out.println("Invalid arguments, try again");
                        }
                        break;


                    case 4:
                        List<Course> courseList = controller.getAllCourses();
                        System.out.println(courseList);
                        break;

                    case 5:
                        try {
                            System.out.println("Enter Course Name:");
                            String courseName3 = reader.nextLine();
                            Course course3 = controller.getCourses().getAll().stream()
                                    .filter(actualCourse -> courseName3.equals(actualCourse.getName()))
                                    .findAny()
                                    .orElseThrow(() -> new NonexistentArgumentException("No course found with the name: " + courseName3));
                            try {
                                List<Course> courseList3 = controller.deleteCourse(course3);
                                System.out.println(courseList3);
                            } catch (NonexistentArgumentException e) {
                                System.out.println("Invalid arguments, try again");
                            } catch (SQLException e3) {
                                System.out.println("Database failed, try again");
                                e3.printStackTrace();
                            }
                            break;
                        } catch (NonexistentArgumentException e) {
                            System.out.println("Invalid arguments, try again");
                        }
                        break;

                    case 6:
                        try {
                            System.out.println("Enter Course Name:");
                            String courseName4 = reader.nextLine();
                            Course course4 = controller.getCourses().getAll().stream()
                                    .filter(actualCourse -> courseName4.equals(actualCourse.getName()))
                                    .findAny()
                                    .orElseThrow(() -> new NonexistentArgumentException("No course found with the name: " + courseName4));
                            System.out.println("Enter New Credits value:");
                            int newCredits = reader.nextInt();
                            try {
                                controller.updateCreditsCourse(course4, newCredits);
                            } catch (NonexistentArgumentException e) {
                                System.out.println("Invalid arguments, try again");
                            } catch (TooManyCreditsException e2) {
                                System.out.println(e2);
                            } catch (SQLException e3) {
                                System.out.println("Database failed, try again");
                                e3.printStackTrace();
                            }
                            break;
                        } catch (NonexistentArgumentException e) {
                            System.out.println("Invalid arguments, try again");
                        }
                        break;

                    case 7:
                        List<Student> sortedStudents = controller.sortStudents();
                        System.out.println(sortedStudents);
                        break;

                    case 8:
                        List<Course> sortedCourses = controller.sortCourses();
                        System.out.println(sortedCourses);
                        break;

                    case 9:
                        List<Student> filteredStudents = controller.filterStudents();
                        System.out.println(filteredStudents);
                        break;

                    case 10:
                        List<Course> filteredCourses = controller.filterCourses();
                        System.out.println(filteredCourses);
                        break;

                    case 11:
                        System.out.println("Quiting...");
                        break loop;

                    default: //when den input is not one of the options,the meniu will be shown once again
                        System.out.println("Input is invalid \n Enter again:");
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid arguments, try again");
                main_menu();
                break;
            }
        }
    }
}