package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import StudentsDB.Student;
import StudentsDB.StudentDao;
import StudentsDB.StudentDatabase;

import static lb.edu.aub.cmps297n_attendance_tracker.Section_Information.COURSE_DATE;

public class List_of_Attendees extends AppCompatActivity {

    StudentDao studentDao;
    StudentDatabase studentDatabase;
    List<Student> studentsList;
    String[] studentArray;
    String[] studentsInfo;
    ListView listView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_attendees);
        listView = (ListView) findViewById(R.id.attendees_list);

        studentDatabase = StudentDatabase.getDatabase(getApplicationContext());
        studentDao = studentDatabase.studentDao();

        final Bundle extras = getIntent().getExtras();
        studentsInfo = extras.getStringArray(COURSE_DATE);

        TextView date = findViewById(R.id.attendance_date);
        date.setText("Date: " + studentsInfo[3]);
        TextView _info = findViewById(R.id.course_title_attendance);
        String currentSection = "User: " + studentsInfo[0] + "\n" + "Course Name: " + studentsInfo[1] + "\n" + "Course Section: " + studentsInfo[2];
        _info.setText(currentSection);
        addStudentsProcess();
    }


    private void addStudentsProcess(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                studentsList =  studentDao.getAllStudents(studentsInfo[0], studentsInfo[1], studentsInfo[2], studentsInfo[3]);
                studentArray = new String[studentsList.size()];

                for(int i=0; i< studentsList.size(); i++){

                    studentArray[i] = "Name: " + studentsList.get(i).getStudent_name() + ", ID: " + studentsList.get(i).getStudent_id() +
                            ", Arrived @ " + studentsList.get(i).getTime();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, studentArray);

        listView.setAdapter(adapter);
    }

    public void backToDates(View view){
        Intent replyIntent = new Intent(this, Section_Information.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}