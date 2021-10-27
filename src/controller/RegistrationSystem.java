package controller;

import model.Teacher;
import model.Course;
import model.Student;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

public class RegistrationSystem {
    private CourseRepository courses;
    private StudentRepository students;
    private TeacherRepository teachers;

    public RegistrationSystem(CourseRepository courses, StudentRepository students, TeacherRepository teachers) {
        this.courses = courses;
        this.students = students;
        this.teachers = teachers;
    }


    public boolean register(Course course, Student student)
            throws AlreadyExistingException, FullCourseException {
        if(course.getStudentsEnrolled().contains(student)){
            throw new AlreadyExistingException("Already registered to this course");  //try-cach o faci unde apelezi functioa
        }
        else
        if (course.getStudentsEnrolled().size() == course.getMaxEnrollment()){
            throw new FullCourseException("The course has no places available");  //try-cach care sa reapeleze functioa cu alt curs, da cu acelasi student
        }
        else{

            int newCreditValue = student.getTotalCredits()+course.getCredits();

            if(newCreditValue > 30){
                throw new TooManyCreditsException("The credits limit has been reached");
            }
            else{
                //update students repo
                student.setTotalCredits(newCreditValue);
                List<Course> studentCourses = student.getEnrolledCourses();
                studentCourses.add(course);
                student.setEnrolledCourses(studentCourses);    //update the course list of the student
                students.update(student);

                //update course repo
                List<Student> courseStudents = course.getStudentsEnrolled();   //add the list of students enrolled in the course
                courseStudents.add(student);    //add the new Student
                course.setStudentsEnrolled(courseStudents);
                courses.update(course);        //update the students list of the course

                return true;
            }
        }
    }

    //in View auch die anzahl von Platze sehen
    public List<Course> retriveCoursesWithFreePlaces(){
        List<Course> freePlacesCourses = new ArrayList<Course>();
        for (Course course : courses.getAll()){
            int freePlaces = course.getMaxEnrollment() - course.getStudentsEnrolled().size();
            if(freePlaces > 0){
                freePlacesCourses.add(course);
            }
        }
        return freePlacesCourses;
    }

    public List<Student> retrieveStudentsEnrolledForACourse(Course course){
        return course.getStudentsEnrolled();
    }

    public List<Course> getAllCourses(){
        return courses.getAll();
    }

}
