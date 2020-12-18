package DatesDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Dates table Entity Skeleton for storing dates per section per course per user
 * With all getters methods
 */
@Entity(tableName = "dates_table")
public class Date {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "course_name")
    private String course_name;

    @NonNull
    @ColumnInfo(name = "course_section")
    private String course_section;



    @NonNull
    @ColumnInfo(name = "date")
    private String date;


    public Date(@NonNull String username, @NonNull String course_name, @NonNull String course_section, @NonNull String date){
        this.username = username;
        this.course_name = course_name;
        this.course_section = course_section;
        this.date = date;
    }

    @Ignore
    public Date(int id, @NonNull String username, @NonNull String course_name, @NonNull String course_section, @NonNull String date){//, , @NonNull int student_id){
        this.id = id;
        this.username = username;
        this.course_name = course_name;
        this.course_section = course_section;
        this.date = date;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getCourse_name(){
        return this.course_name;
    }

    public String getCourse_section(){
        return this.course_section;
    }

    public String getDate(){
        return this.date;
    }
}