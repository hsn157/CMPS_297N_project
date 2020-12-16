package StudentsDB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


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
    public String date;


    @NonNull
    @ColumnInfo(name = "student_name")
    public String student_name;


    @NonNull
    @ColumnInfo(name = "student_id")
    public int student_id;


    public Student(@NonNull String username, @NonNull String course_name, @NonNull String course_section, @NonNull String date, @NonNull String student_name,
                   @NonNull int student_id ){
        this.username = username;
        this.course_name = course_name;
        this.course_section = course_section;
        this.date = date;
        this.student_name = student_name;
        this.student_id = student_id;
    }



    @Ignore
    public Student(int id, @NonNull String username, @NonNull String course_name, @NonNull String course_section, @NonNull String date, @NonNull String student_name,
                   @NonNull int student_id ){
        this.id = id;
        this.username = username;
        this.course_name = course_name;
        this.course_section = course_section;
        this.date = date;
        this.student_name = student_name;
        this.student_id = student_id;
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
}