package controller;

import View.KonsoleView;
import model.Course;
import model.Student;
import model.Teacher;
import repository.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    private CourseFileRepo courses;
    private StudentFileRepo students;
    private TeacherFileRepo teachers;
    private KonsoleView view;

    public Controller(CourseFileRepo courses, StudentFileRepo students, TeacherFileRepo teachers, KonsoleView view) {
        this.courses = courses;
        this.students = students;
        this.teachers = teachers;
        this.view = view;
    }

    /**
     * Registers a student to a specific course
     * It adds the course to the list of courses of the student in the student repository +
     * adds the student to the list of students in the course repository
     * @param course the course to which somebody wants to register
     * @param student the student who wants to register to the course
     * @return true if all repos have been updated successfully
     * @throws NonexistentArgumentException if the given student or course does not exist in the according repository
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    public boolean register(Course course, Student student) throws NonexistentArgumentException, IOException {
        if(!students.getAll().contains(student)){
            throw new NonexistentArgumentException("No such student");
        }
        else
        if(!courses.getAll().contains(course)){
            throw new NonexistentArgumentException("No such course");
        }
        else {

            //update students REPO

            //update the course list of the student
            student.addCourse(course);
            students.update(student);


            //update course REPO
            course.addStudent(student);
            courses.update(course);        //update the students list of the course

            return true;
        }
    }

    /**
     * Gives a list of courses which at the moment have free places - the number of enrolled students is less than the courses macEnrolled argument
     * @return a list of courses
     */
    public List<Course> retriveCoursesWithFreePlaces(){
        List<Course> freePlacesCourses = new ArrayList<>();
        for (Course course : courses.getAll()){
            int freePlaces = course.getMaxEnrollment() - course.getStudentsEnrolled().size();
            if(freePlaces > 0){
                freePlacesCourses.add(course);
            }
        }
        //TODO in View display nr of free places
        return freePlacesCourses;
    }


    /***
     * Gives a list of all students enrolled in a given course found in the course
     * @param course the course from which we want the students enrolled
     * @return a list of students
     * @throws NonexistentArgumentException if the given course does not exist is the courses repository
     */
    public List<Student> retrieveStudentsEnrolledForACourse(Course course) throws NonexistentArgumentException {
        if(courses.getAll().contains(course)){
            return course.getStudentsEnrolled();
        }
        else
            throw new NonexistentArgumentException("No such course");
    }

    /**
     * Gives a list of all courses in the courses repository
     * @return a list of courses
     */
    public List<Course> getAllCourses(){
        return courses.getAll();
    }


    /**
     * Removes a course from the course repository, as well as from the corresponding teacher list, and every student
     * that is enrolled to it (modifying also the total credits of each student - through removeCourse)
     * @param course the course to be deleted
     * @return the modified list of courses
     * @throws NonexistentArgumentException if the given course does not exist in te courses list
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    public List<Course> deleteCourse(Course course) throws NonexistentArgumentException, IOException {
        if(courses.getAll().contains(course)){
            //delete from the teacher REPO
            Teacher teacher = (Teacher) course.getTeacher();
            teacher.removeCourse(course);
            teachers.update(teacher);

            for (Student student: students.getAll()){       //delete from every student's list, the course
                List<Course> studentCourses = student.getEnrolledCourses();

                if(studentCourses.contains(course)){
                    student.removeCourse(course);

                    //update the students REPO
                    students.update(student);
                }
            }

            //delete from the course REPO
            courses.delete(course);

            return courses.getAll();
        }
        else {
            throw new NonexistentArgumentException("No such course");
        }

    }

    /***
     * A teacher can update the number of credits of his/her course, automaticly modifying the value of the total value of credits
     * for each student enrolled by his/her course - if the total credits of the student has been reached,
     * the course will be automatically be taken out - through student.updateCourse()
     * @param course the course to be updated
     * @param newCredits the new value for numbers of credits
     * @return the updated list of courses
     * @throws NonexistentArgumentException if the given course does not exist in te courses list
     * @throws IOException  if there occurs an error with the ObjectOutputStream
     */
    public List<Course> updateCreditsCourse(Course course, int newCredits) throws NonexistentArgumentException, IOException {
        if(courses.getAll().contains(course)) {
            //update student REPO
            for (Student student : course.getStudentsEnrolled()) {
                if (student.getEnrolledCourses().contains(course)) {
                    student.updateCredits(course, newCredits);
                    students.update(student);
                }
            }

            //credits number will be updated automatic in the Courses in every repo
            //update the course credits in the course REPO
            course.setCredits(newCredits);
            courses.updateCredits(course);

            return courses.getAll();
        }
        else{
            throw new NonexistentArgumentException("No such course");
        }
    }

    /**
     * Sorts the list of Students from the students Repo, according to firstName
     * @return the sorted list of students
     */
    public  List<Student> sortStudents(){
        List<Student> studentsList = students.getAll();
        Collections.sort(studentsList);
        return studentsList;
    }

    /**
     * Sorts the list of Courses from the courses Repo, according to name
     * @return the sorted list of courses
     */
    public List <Course> sortCourses(){
        List<Course> courseList = courses.getAll();
        Collections.sort(courseList);
        return courseList;
    }

    /**
     * Filters the list of Students from students Repo, whose firstname starts with a "B"
     * @return a list of Students, whose firstname starts with a "B"
     */
    public List<Student> filterStudents(){

        return students.getAll().stream()
                .filter(entry -> entry.getFirstName().startsWith("B"))
                .collect(Collectors.toList());
    }

    /**
     * Filters the list of Students from courses Repo, whose credits are greater than 5
     * @return a list of Courses, whose credits are greater than 5
     */
    public List<Course> filterCourses(){

        return courses.getAll().stream()
                .filter(entry -> entry.getCredits() >5 )
                .collect(Collectors.toList());
    }

}
