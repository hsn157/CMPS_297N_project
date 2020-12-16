package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import CoursesDB.Course;
import CoursesDB.CourseDao;
import CoursesDB.CourseDatabase;
import DatesDB.Date;
import DatesDB.DateDao;
import DatesDB.DateDatabase;

import static lb.edu.aub.cmps297n_attendance_tracker.List_Courses.ADD_COURSE_REQUEST_CODE;
import static lb.edu.aub.cmps297n_attendance_tracker.List_Courses.COURSE_INFO;

public class Section_Information extends AppCompatActivity {


    String[] datesArray;
    List<Date> datesList;

    ArrayAdapter<String> adapter;
    ListView listView;
    DateDao dateDao;
    DateDatabase dateDatabase;
    String[] courseInfo;
    Bundle extras;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    public static String COURSE_DATE = "course_info_with_date";
    public static int ADD_STUDENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_information);
        listView = (ListView) findViewById(R.id.dates_list);

        dateDatabase = DateDatabase.getDatabase(getApplicationContext());
        dateDao = dateDatabase.dateDao();
        extras = getIntent().getExtras();
        courseInfo = extras.getStringArray(COURSE_INFO);

        addDateProcess();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenSectionInfo(view);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void OpenSectionInfo(View view){
        Intent intent = new Intent(this, List_of_Attendees.class);
        String[] info = new String[4];
        for(int i=0; i<3; i++){
            info[i] = courseInfo[i];
        }
        setDate();
        info[3] = date;
        intent.putExtra(COURSE_DATE, info);

        startActivityForResult(intent, ADD_STUDENT);
    }

    public void TakeAttendance(View view){
        setDate();
        Thread t = new Thread(new Runnable() {

            @Override
            public void run(){
                if(!dateDao.checkDateExists(courseInfo[0], courseInfo[1], courseInfo[2], date)){

                    dateDao.insert(new Date(courseInfo[0], courseInfo[1], courseInfo[2], date));
                }
            }

        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addDateProcess();
        Intent intent = new Intent(this, Take_Attendance.class);
        String[] info = new String[4];
        for(int i=0; i<3; i++){
           info[i] = courseInfo[i];
        }
        info[3] = date;
        intent.putExtra(COURSE_DATE, info);
        startActivity(intent);
    }

    private void setDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
    }

    private void addDateProcess(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                datesList =  dateDao.getAllDates(courseInfo[0], courseInfo[1], courseInfo[2]);
                datesArray = new String[datesList.size()];

                for(int i=0; i< datesList.size(); i++){

                    datesArray[i] = datesList.get(i).getDate();
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
                android.R.layout.simple_list_item_1, datesArray);

        listView.setAdapter(adapter);
    }
    public void backToCourses(View view){
        Intent replyIntent = new Intent(this, List_Courses.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}


