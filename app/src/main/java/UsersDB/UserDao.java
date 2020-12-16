package UsersDB;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);
    /*
    @Query("SELECT * FROM users_table WHERE EXISTS (SELECT username FROM users_table WHERE username = :user)")
    boolean checkUserExists(String user);

    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username AND password = :password)")
    boolean checkPassword(String username, String password);
    */
    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username)")
    boolean checkUserExists(String username);

    @Query("SELECT EXISTS (SELECT * FROM users_table WHERE username = :username AND password = :password)")
    boolean checkPassword(String username, String password);
}
