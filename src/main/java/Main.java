import controller.Controller;
import repository.*;
import view.KonsoleView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    static final String DB_URL = "jdbc:mysql://localhost:3306/lab5database";
    static final String USER = "root";
    static final String PASS = "password";

    public static void resetDatabase(Statement stmt) throws SQLException {
        stmt.executeUpdate("DELETE FROM studenten_course;");
        stmt.executeUpdate("UPDATE student SET totalCredits = "+ 0 + ";");
        stmt.executeUpdate("DELETE FROM course;");
        stmt.executeUpdate("INSERT INTO course VALUES (1,'c1',1,2,10), (2,'c2',1,2,10), (3,'c3',2,2,11)");
    }

    public static void main(String[] args){
        try{Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            resetDatabase(stmt);  //we can reset the database, to start from 0
                                    //work also without it

            TeacherJDBCRepository teacherJDBCRepository = new TeacherJDBCRepository(conn);
            StudentJDBCRepository studentJDBCRepository = new StudentJDBCRepository(conn);
            CourseJDBCRepository courseJDBCRepository = new CourseJDBCRepository(conn);

            Controller controller = new Controller(courseJDBCRepository,studentJDBCRepository,teacherJDBCRepository);

            KonsoleView view = new KonsoleView(controller);
            view.main_menu();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }




    }

}
