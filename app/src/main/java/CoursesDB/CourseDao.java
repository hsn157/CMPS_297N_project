package CoursesDB;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Interface to interact with the courses DB
 *
 * Insert a new course to the DB
 *
 * Query to get all courses for a specific user
 *
 * Query to check if a section, for a specific user already exits
 */
@Dao
public interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Query("SELECT * FROM courses_table WHERE username = :username")
    List<Course> getAllCourses(String username);

    @Query("SELECT EXISTS (SELECT * FROM courses_table WHERE username = :username AND course_name = :course_name AND course_section = :course_section)")
    boolean checkCourseExists(String username, String course_name, String course_section);

}
