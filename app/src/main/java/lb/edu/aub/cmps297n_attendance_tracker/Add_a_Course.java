package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Add_a_Course extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_course);
    }

    public void BackToCourseList(View view){
        Intent intent = new Intent(this, List_Courses.class);
        startActivity(intent);
    }
}