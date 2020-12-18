package StudentsDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Student table Entity that previews the skeleton
 * With getters methods
 */

@Entity(tableName = "students_table")
public class Student {

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


    @NonNull
    @ColumnInfo(name = "student_name")
    private String student_name;


    @NonNull
    @ColumnInfo(name = "student_id")
    private int student_id;

    @NonNull
    @ColumnInfo(name = "hours")
    private int hours;

    @NonNull
    @ColumnInfo(name = "minutes")
    private int minutes;

    @NonNull
    @ColumnInfo(name = "am_pm")
    private String am_pm;

    public Student(@NonNull String username, @NonNull String course_name, @NonNull String course_section, @NonNull String date, @NonNull String student_name,
                   @NonNull int student_id, @NonNull int hours,  @NonNull int minutes, @NonNull String am_pm){
        this.username = username;
        this.course_name = course_name;
        this.course_section = course_section;
        this.date = date;
        this.student_name = student_name;
        this.student_id = student_id;
        this.hours = hours;
        this.minutes = minutes;
        this.am_pm = am_pm;
    }



    @Ignore
    public Student(int id, @NonNull String username, @NonNull String course_name, @NonNull String course_section, @NonNull String date, @NonNull String student_name,
                   @NonNull int student_id, @NonNull int hours,  @NonNull int minutes, @NonNull String am_pm){
        this.id = id;
        this.username = username;
        this.course_name = course_name;
        this.course_section = course_section;
        this.date = date;
        this.student_name = student_name;
        this.student_id = student_id;
        this.hours = hours;
        this.minutes = minutes;
        this.am_pm = am_pm;

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

    public String getStudent_name(){
        return this.student_name;
    }

    public int getStudent_id(){
        return this.student_id;
    }

    public int getHours(){
        return this.hours;
    }

    public int getMinutes(){
        return this.minutes;
    }

    public String getAm_pm(){
        return this.am_pm;
    }
}