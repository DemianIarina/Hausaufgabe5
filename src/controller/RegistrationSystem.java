package controller;

import model.Teacher;
import model.Course;
import model.Student;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents all the actions of an university, that can be acted upon the 3 repositorys - courses, teachers and students
 */
public class RegistrationSystem {
    private CourseRepository courses;
    private StudentRepository students;
    private TeacherRepository teachers;

    public RegistrationSystem(CourseRepository courses, StudentRepository students, TeacherRepository teachers) {
        this.courses = courses;
        this.students = students;
        this.teachers = teachers;
    }

    /**
     * Registers a student to a specific course
     * It adds the course to the list of courses of the student in the student repository +
     * adds the student to the list of students in the course repository
     * @param course the course to which somebody wants to register
     * @param student the student who wants to register to the course
     * @return true if all repos have been updated successfully
     * @throws IllegalArgumentException if the given student or course does not exist in the according repository
     */
    public boolean register(Course course, Student student) throws IllegalArgumentException {
        if(!students.getAll().contains(student)){
            throw new IllegalArgumentException("No such student");
        }
        else
            if(!courses.getAll().contains(course)){
                throw new IllegalArgumentException("No such course");
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
     * @throws IllegalArgumentException if the given course does not exist is the courses repository
     */
    public List<Student> retrieveStudentsEnrolledForACourse(Course course) throws IllegalArgumentException{
        if(courses.getAll().contains(course)){
            return course.getStudentsEnrolled();
        }
        else
            throw new IllegalArgumentException("No such course");
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
     * @throws IllegalArgumentException if the given course does not exist in te courses list
     */
    public List<Course> deleteCourse(Course course) throws IllegalArgumentException{
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
            throw new IllegalArgumentException("No such course");
        }

    }

    /**
     * A teacher can update the number of credits of his/her course, automaticly modifying the value of the total value of credits
     * for each student enrolled by his/her course - if the total credits of the student has been reached,
     * the course will be automatically be taken out - through student.updateCourse()
     * @param course the course to be updated
     * @param newCredits the new value for numbers of credits
     * @return the updated list of courses
     */
    public List<Course> updateCreditsCourse(Course course, int newCredits){
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
            throw new IllegalArgumentException("No such course");
        }
    }

    public  List<Student> sortStudents(){
        List<Student> studentsList = students.getAll();
        Collections.sort(studentsList);
        return studentsList;
    }

    public List <Course> sortCourses(){
        List<Course> courseList = courses.getAll();
        Collections.sort(courseList);
        return courseList;
    }



}
