package model;

/**
 * A pair of the id of a course and the number of credits
 * used for easier management of Courses in a student object
 */
public class Pair {
    private int courseId;
    private int credits;

    public Pair(int courseId, int credits) {
        this.courseId = courseId;
        this.credits = credits;
    }

    public int getCourseId() {
            return courseId;
        }

    public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

    public int getCredits() {
            return credits;
        }

    public void setCredits(int credits) {
            this.credits = credits;
        }

    @Override
    public String toString() {
        return "Pair{" +
                    "courseId=" + courseId +
                    ", credits=" + credits +
                    '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return courseId == pair.courseId && credits == pair.credits;
    }

}
