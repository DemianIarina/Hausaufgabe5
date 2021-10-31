package controller;

import model.Teacher;
import model.Course;
import model.Student;
import repository.CourseRepository;
import repository.StudentRepository;
import repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            throws IllegalArgumentException{
        if(!students.getAll().contains(student)){
            throw new IllegalArgumentException("No such Student");
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

    //in View auch die anzahl von Platze sehen
    public List<Course> retriveCoursesWithFreePlaces(){
        List<Course> freePlacesCourses = new ArrayList<Course>();
        for (Course course : courses.getAll()){
            int freePlaces = course.getMaxEnrollment() - course.getStudentsEnrolled().size();
            if(freePlaces > 0){
                freePlacesCourses.add(course);
            }
        }
        //TODO in View display nr of free places
        return freePlacesCourses;
    }

    public List<Student> retrieveStudentsEnrolledForACourse(Course course){
        return course.getStudentsEnrolled();
    }

    public List<Course> getAllCourses(){
        return courses.getAll();
    }

    public List<Course> deleteCourse(Course course){
        //delete from the teacher REPO
        Teacher teacher = (Teacher) course.getTeacher();
        teacher.removeCourse(course);
        teachers.update(teacher);

        for (Student student: students.getAll()){       //delete from every student's list, the course
            List<Course> studentCourses = student.getEnrolledCourses();

            if(studentCourses.contains(course)){
                student.removeCouse(course);

                //update the students REPO
                students.update(student);
            }
        }

        //delete from the course REPO
        courses.delete(course);

        return courses.getAll();
    }

    public List<Course> updateCreditsCourse(Course course, int newCredits){
        //credits number will be updated automatic in the Courses everywhere

        //update student REPO
        for(Student student: course.getStudentsEnrolled()){
            if(student.getEnrolledCourses().contains(course)){
                student.updateCredits(course, newCredits);
                students.update(student);
            }
        }

        //update the course credits in the course REPO
        course.setCredits(newCredits);
        courses.updateCredits(course);

        return courses.getAll();
    }

}
