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
            throws AlreadyExistingException, FullCourseException {
        if(course.getStudentsEnrolled().contains(student)){
            throw new AlreadyExistingException("Already registered to this course");  //try-cach o faci unde apelezi functioa
        }
        else
            if (course.getStudentsEnrolled().size() == course.getMaxEnrollment()){
                throw new FullCourseException("The course has no places available");  //try-cach care sa reapeleze functioa cu alt curs, da cu acelasi student
            }
            else{

                int newCreditValue = student.getTotalCredits() + course.getCredits();

                if(newCreditValue > 30){
                    throw new TooManyCreditsException("The credits limit has been reached for " + student.getStudentId());
                }
                else{
                    //update students REPO

                    //update the course list of the student
                    List<Course> studentCourses = student.getEnrolledCourses();
                    studentCourses.add(course);
                    student.setEnrolledCourses(studentCourses);

                    //update the number of credits of the student
                    student.setTotalCredits(newCreditValue);

                    students.update(student);


                    //update course REPO
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

    public List<Course> deleteCourse(Course course){
        //delete from the course REPO
        courses.delete(course);

        //delete from the teacher REPO
        Teacher teacher = (Teacher) course.getTeacher();
        List<Course> teacherCourses = teacher.getCourses();
        teacherCourses.remove(course);
        teachers.update(teacher);

        for (Student student: students.getAll()){       //delete from every student's list, the course
            List<Course> studentCourses = student.getEnrolledCourses();

            if(studentCourses.contains(course)){
                //delete the course from the student's list
                studentCourses.remove(course);
                student.setEnrolledCourses(studentCourses);

                //update the number of credits of the student
                int newCreditValue = student.getTotalCredits() - course.getCredits();
                student.setTotalCredits(newCreditValue);


                //update the students REPO
                students.update(student);
            }
        }
        return courses.getAll();
    }

    public List<Course> updateCreditsCourse(Course course){
        //update the course credits in the course REPO
        courses.updateCredits(course);

        //update teacher REPO
        Teacher teacher = (Teacher) course.getTeacher();
        List<Course> teacherCourses = teacher.getCourses();

        for(Course actualCourse: teacherCourses){
            if(Objects.equals(actualCourse.getName(), course.getName())){
                teacherCourses.remove(actualCourse);   //remove the old course, with the old value of credits
                teacherCourses.add(course);            //add the new course

                                                        //TODO: modificat cu un set credits la actualCourse
                break;
            }
        }
        teacher.setCourses(teacherCourses);

        teachers.update(teacher);

        //update student REPO
        for(Student student: course.getStudentsEnrolled()){
            List<Course> studentCourses = student.getEnrolledCourses();

            //update the nr of credits of the student -> 2 possibilities (ramane sub 30 de credite, or not)
            //update the course list of the student
            for(Course actualCourse: studentCourses) {
                if (Objects.equals(actualCourse.getName(), course.getName())) {
                    int newCreditValue = student.getTotalCredits() - actualCourse.getCredits() + course.getCredits();

                    if(newCreditValue <= 30){
                        student.setTotalCredits(newCreditValue);

                        studentCourses.remove(actualCourse);
                        studentCourses.add(course);
                        student.setEnrolledCourses(studentCourses);
                        break;
                    }
                    else{
                        //remove the course from the students list + throw to
                        studentCourses.remove(actualCourse);
                        throw new TooManyCreditsException("The credits limit has been reached for "+ student.getStudentId() +". Course has been deleted!");

                    }
                }
            }

            students.update(student);

        }

        return courses.getAll();
    }

}
