package UsersDB;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
/**
 * User table Entity that previews the skeleton
 * With getters methods
 */
@Entity(tableName = "users_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    public User(@NonNull String username, @NonNull String password){
        this.username = username;
        this.password = password;
    }

      @Ignore
      public User(int id, @NonNull String username, @NonNull String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}