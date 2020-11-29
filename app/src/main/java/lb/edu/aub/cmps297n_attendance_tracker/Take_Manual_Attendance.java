package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Take_Manual_Attendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_manual_attendance);
    }


    public void backfornow(View view){
       Intent intent = new Intent(this,List_Courses.class );
       startActivity(intent);
    }
}