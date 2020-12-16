package DatesDB;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Date date);

    @Query("SELECT * FROM dates_table WHERE username = :username AND course_name = :course_name AND course_section = :course_section ORDER BY id ASC")
    List<Date> getAllDates(String username, String course_name, String course_section);

    @Query("SELECT EXISTS (SELECT * FROM dates_table WHERE username = :username AND course_name = :course_name AND" +
            " course_section = :course_section AND date = :date)")
    boolean checkDateExists(String username, String course_name, String course_section, String date);


    /*
    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username)")
    boolean checkUserExists(String username);

    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username AND password = :password)")
    boolean checkPassword(String username, String password);

     */
}
