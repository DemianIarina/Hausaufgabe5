package model;

import controller.AlreadyExistingException;
import controller.FullCourseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a subject taught in a university by one specific teacher, and has a credits vaue
 * There can be none to as many students enrolled, as in maxEnrolled specified
 */
public class Course {

        private String name;
        private Person teacher;
        private int maxEnrollment;
        private List<Student> studentsEnrolled;
        private int credits;

        public Course(String name, Person teacher, int maxEnrollment, int credits) {
            this.name = name;
            this.teacher = teacher;
            this.maxEnrollment = maxEnrollment;
            this.credits = credits;
            this.studentsEnrolled = new ArrayList<Student>();
        }

        /**
         * Adds a new student to the course list
         * @param student a new student object
         * @throws AlreadyExistingException if the student already enrolled in the course is
         * @throws FullCourseException if the max number of enrolled students has been reached
         */
        public void addStudent(Student student) throws AlreadyExistingException, FullCourseException{
            if(studentsEnrolled.contains(student)){
                throw new AlreadyExistingException("Already registered to this course");  //try-cach o faci unde apelezi functioa
            }
            else
                if(studentsEnrolled.size() == maxEnrollment){
                throw new FullCourseException("The course has no places available");  //try-cach care sa reapeleze functioa cu alt curs, da cu acelasi student
            }
            studentsEnrolled.add(student);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person getTeacher() {
            return teacher;
        }

        public void setTeacher(Person teacher) {
            this.teacher = teacher;
        }

        public int getMaxEnrollment() {
            return maxEnrollment;
        }

        public void setMaxEnrollment(int maxEnrollment) {
            this.maxEnrollment = maxEnrollment;
        }

        public List<Student> getStudentsEnrolled() {
            return studentsEnrolled;
        }

        public void setStudentsEnrolled(List<Student> studentsEnrolled) {
            this.studentsEnrolled = studentsEnrolled;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", teacher=" + teacher +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }
}
