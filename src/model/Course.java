package model;

import java.util.List;

public class Course {

        private String name;
        private Person teacher;
        private int maxEnrollment;
        private List<Student> studentsEnrolled;
        private int credits;

        public Course(String name, Person teacher, int maxEnrollment, List<Student> studentsEnrolled, int credits) {
            this.name = name;
            this.teacher = teacher;
            this.maxEnrollment = maxEnrollment;
            this.studentsEnrolled = studentsEnrolled;
            this.credits = credits;
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
}
