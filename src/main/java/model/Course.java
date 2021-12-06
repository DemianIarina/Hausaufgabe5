package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import controller.AlreadyExistingException;
import controller.FullCourseException;
import controller.NonexistentArgumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a subject taught in a university by one specific teacher, and has a credits value
 * There can be none to as many students enrolled, as in maxEnrolled specified
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@json_id")
public class Course implements Comparable<Course>{
        private int id;
        private String name;
        private int idTeacher;
        private int maxEnrollment;
        private List<Integer> studentsEnrolledId;
        private int credits;

        public Course(int id,String name, int idTeacher, int maxEnrollment, int credits) {
            this.id = id;
            this.name = name;
            this.idTeacher = idTeacher;
            this.maxEnrollment = maxEnrollment;
            this.credits = credits;
            this.studentsEnrolledId = new ArrayList<>();
        }

        public Course(int id, int credits){
            this.id = id;
            this.credits = credits;
        }


        public Course(){}

        /**
         * Adds a new student to the course list
         * @param studentId a new student object
         * @throws AlreadyExistingException if the student already enrolled in the course is
         * @throws FullCourseException if the max number of enrolled students has been reached
         */
        public void addStudent(int studentId) throws AlreadyExistingException, FullCourseException{
            if(studentsEnrolledId.contains(studentId)){
                throw new AlreadyExistingException("Already registered to this course");
            }
            else
                if(studentsEnrolledId.size() == maxEnrollment){
                throw new FullCourseException("The course has no places available");
            }
            studentsEnrolledId.add(studentId);
        }

    public void removeStudent(int studentId) throws NonexistentArgumentException {
        if(!studentsEnrolledId.contains(studentId)){
            throw new AlreadyExistingException("Not registered to this course");
        }

        studentsEnrolledId.remove(studentId);
    }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIdTeacher() {
            return idTeacher;
        }

        public void setIdTeacher(int idTeacher) {
            this.idTeacher = idTeacher;
        }

        public int getMaxEnrollment() {
            return maxEnrollment;
        }

        public void setMaxEnrollment(int maxEnrollment) {
            this.maxEnrollment = maxEnrollment;
        }

        public List<Integer> getStudentsEnrolledId() {
            return studentsEnrolledId;
        }

        public void setStudentsEnrolledId(List<Integer> studentsEnrolledId) {
            this.studentsEnrolledId = studentsEnrolledId;
        }

        public int getCredits() {
            return credits;
        }

        public void setCredits(int credits) {
            this.credits = credits;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    @Override
    public String toString() {
        return "Course{" +
                "id = "+ id +
                ", name='" + name + '\'' +
                ", idTeacher=" + idTeacher+
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolledId=" + studentsEnrolledId +
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
