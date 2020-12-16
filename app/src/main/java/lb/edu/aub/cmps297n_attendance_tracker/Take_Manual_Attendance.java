package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Take_Manual_Attendance extends AppCompatActivity {

    EditText _id;
    EditText _name;

    public static final String STUDENT_NAME = "student_name";
    public static final String STUDENT_ID = "student_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_manual_attendance);

        _id = findViewById(R.id.enter_id);
        _name = findViewById(R.id.enter_name);
    }


    public void takeAttendance(View view){

        if(_id.getText().toString().isEmpty() || _name.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter all Fields", Toast.LENGTH_LONG).show();
            return;
        }
        if(_id.getText().toString().length() != 9 ) {
            Toast.makeText(this, "Enter a valid ID", Toast.LENGTH_LONG).show();
            return;
        }
       Intent intent = new Intent(this,Take_Attendance.class );
        setResult(RESULT_OK, intent);
        int id = Integer.parseInt(_id.getText().toString());
        String name = _name.getText().toString();
        intent.putExtra(STUDENT_ID, id);
        intent.putExtra(STUDENT_NAME, name);


        finish();

    }
}