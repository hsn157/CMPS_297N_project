package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add_a_Course extends AppCompatActivity {
    private EditText course_name;
    private EditText course_section;

    public static final String REPLY_COURSE_NAME = "course_name_id";
    public static final String REPLY_COURSE_SECTION = "course section id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_course);

        course_name = findViewById(R.id.add_course_name);
        course_section = findViewById(R.id.add_course_section);
    }

    public void AddToCourseList(View view){
        Intent replyIntent = new Intent(this, List_Courses.class);
        if(course_name.getText().toString().isEmpty() || course_section.getText().toString().isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }

        else{
            String course_n = course_name.getText().toString();
            String course_s = course_section.getText().toString();

            replyIntent.putExtra(REPLY_COURSE_NAME, course_n);
            replyIntent.putExtra(REPLY_COURSE_SECTION, course_s);
            setResult(RESULT_OK, replyIntent);
            finish();

        }
    }

    public void BackToCourseList(View view){
        Intent replyIntent = new Intent(this, List_Courses.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}

