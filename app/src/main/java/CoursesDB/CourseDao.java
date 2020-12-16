package CoursesDB;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Query("SELECT * FROM courses_table WHERE username = :username")
    List<Course> getAllCourses(String username);

    @Query("SELECT EXISTS (SELECT * FROM courses_table WHERE username = :username AND course_name = :course_name AND course_section = :course_section)")
    boolean checkCourseExists(String username, String course_name, String course_section);

    /*
    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username)")
    boolean checkUserExists(String username);

    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username AND password = :password)")
    boolean checkPassword(String username, String password);

     */
}
