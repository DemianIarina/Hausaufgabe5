package repository;

import model.Student;

/**
 *  Represents an in memory repository for students
 */
public class StudentRepository extends InMemoryRepository<Student> {
    public StudentRepository(){
        super();
    }

    /**
     * Modifies the courses list and the number of credits from a student in the repository, found by id
     * @param obj a student with the new courses and number of credits
     * @return modified student
     */
    @Override
    public Student update(Student obj) {    //used by adding and by deleting a course
        Student studentToUpdate = this.repoList.stream()
                .filter(student -> student.getStudentId() == obj.getStudentId())
                .findFirst()
                .orElseThrow();

        studentToUpdate.setEnrolledCourses(obj.getEnrolledCourses());
        studentToUpdate.setTotalCredits(obj.getTotalCredits());

        return studentToUpdate;
    }
}
