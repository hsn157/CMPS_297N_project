package StudentsDB;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import DatesDB.Date;

/**
 * Interface to:
 *
 * Insert a Student
 *
 * Query and get All Students related to a specific username - course name - course section - date of attendance,
 * and ordering these students by TIME each one of them attended
 *
 * Query and check if student exists, providing all needed info: concerned username - course name - course section - date of attendance - student_id
 */
@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Student student);

    @Query("SELECT * FROM students_table WHERE username = :username AND course_name = :course_name AND course_section = :course_section " +
            "AND date = :date ORDER BY am_pm, hours, minutes")
    List<Student> getAllStudents(String username, String course_name, String course_section, String date);

    @Query("SELECT EXISTS (SELECT * FROM students_table WHERE username = :username AND course_name = :course_name AND" +
            " course_section = :course_section AND date = :date AND student_id = :student_id)")
    boolean checkStudentExists(String username, String course_name, String course_section, String date, int student_id);


    /*
    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username)")
    boolean checkUserExists(String username);

    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username AND password = :password)")
    boolean checkPassword(String username, String password);

     */
}
