package DatesDB;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Interface to interact with the DB
 *
 * Insert a new Date
 *
 * Query All dates, for a specific user and course name/section
 *
 * Query and check if a date already exists, for a specific user and course name/section
 */
@Dao
public interface DateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Date date);

    @Query("SELECT * FROM dates_table WHERE username = :username AND course_name = :course_name AND course_section = :course_section ORDER BY id ASC")
    List<Date> getAllDates(String username, String course_name, String course_section);

    @Query("SELECT EXISTS (SELECT * FROM dates_table WHERE username = :username AND course_name = :course_name AND" +
            " course_section = :course_section AND date = :date)")
    boolean checkDateExists(String username, String course_name, String course_section, String date);

}
