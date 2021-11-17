package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import controller.AlreadyExistingException;
import controller.FullCourseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a subject taught in a university by one specific teacher, and has a credits vaue
 * There can be none to as many students enrolled, as in maxEnrolled specified
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property="@UUID")
public class Course implements Comparable<Course>{

        private String name;
        @JsonBackReference
        private Teacher teacher;
        private int maxEnrollment;
        private List<Student> studentsEnrolled;
        private int credits;

        public Course(String name, Teacher teacher, int maxEnrollment, int credits) {
            this.name = name;
            this.teacher = teacher;
            this.maxEnrollment = maxEnrollment;
            this.credits = credits;
            this.studentsEnrolled = new ArrayList<>();
        }

        public Course(){}

        /**
         * Adds a new student to the course list
         * @param student a new student object
         * @throws AlreadyExistingException if the student already enrolled in the course is
         * @throws FullCourseException if the max number of enrolled students has been reached
         */
        public void addStudent(Student student) throws AlreadyExistingException, FullCourseException{
            if(studentsEnrolled.contains(student)){
                throw new AlreadyExistingException("Already registered to this course");
            }
            else
                if(studentsEnrolled.size() == maxEnrollment){
                throw new FullCourseException("The course has no places available");
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

        public void setTeacher(Teacher teacher) {
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
                ", teacher=" + teacher.toString() +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }

    /**
     * Function to compare two instances of Course, according to name
     * @param course2 another object of type Course
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Course course2) {
        return this.getName().compareTo(course2.getName());
    }
}
