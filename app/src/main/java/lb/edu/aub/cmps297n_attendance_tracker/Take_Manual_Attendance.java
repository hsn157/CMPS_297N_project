package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class
Take_Manual_Attendance extends AppCompatActivity {

    EditText _id; //Edit text to read the id of the student from the view
    EditText _name; //Edit text to read the name of the student from the view

    public static final String STUDENT_NAME = "student_name"; //student_name extras id to be put in
    public static final String STUDENT_ID = "student_id";//student_id extra id to be put in

    /**
     * Initialze the edit text view and the layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_manual_attendance);

        _id = findViewById(R.id.enter_id);
        _name = findViewById(R.id.enter_name);
    }

    /**
     * Handle the OnClick of the take Attendance button
     * Check if all fields are filled
     * Checks if the ID is 9 digits (AUB students' IDs are usually 9-digits)
     * Toast messages previews the result of the route taken
     * Put the name and id of the student in an extra string and pass them to the Take_Attendace Activity
     * @param view
     */
    public void takeAttendance(View view){

        if(_id.getText().toString().isEmpty() || _name.getText().toString().isEmpty()){ //Check if all fields are filled
            Toast.makeText(this, "Enter all Fields", Toast.LENGTH_LONG).show();
            return;
        }
        if(_id.getText().toString().length() != 9 ) { //checks if the ID is 9 digits (AUB students' IDs are usually 9-digits)
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
    public void go_Back(View view){
        finish();
    }
}