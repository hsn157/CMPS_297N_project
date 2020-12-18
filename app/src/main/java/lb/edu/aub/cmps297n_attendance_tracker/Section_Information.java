package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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


    String[] datesArray; //Array to dates of attended date, to be bind with the adapter
    List<Date> datesList; //List to store retrieved dates from the DB

    ArrayAdapter<String> adapter; //Adapter to bind dates data in the DB to the list view

    ListView listView;
    DateDao dateDao;
    DateDatabase dateDatabase;

    String[] courseInfo; //To store info from previous activities via bundle's extras
    Bundle extras; //Store the extras in order to pass it to further activities

    TextView course_data; //Title of the screen text view, to be updated upon the current user and course name/

    //The three following global variables are used to retrieved live time, in a specific format, and store it in String 'date'
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    public static String COURSE_DATE = "course_info_with_date";  //ID to be bind with the course_date value as an extra for the next intent
    public static int ADD_STUDENT = 1; //request code ID for adding a student intent Activity

    /**
     * Initialize list view.
     * Initialize course_title label to a text view reference.
     * Initialize DB and Dao.
     * Retrieve all the extras related to the course info, and store them in an array.
     * Parsing data according to the specific user/course name/ section info retrieved from the extras.
     * Call the add data process in order to retrieve all dates from the DB related for this instance,
     * and present them in the list view, after binding the concerned array to the adapter
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_information);
        listView = (ListView) findViewById(R.id.dates_list);
        course_data = findViewById(R.id.course_title);


        dateDatabase = DateDatabase.getDatabase(getApplicationContext());
        dateDao = dateDatabase.dateDao();
        extras = getIntent().getExtras();
        courseInfo = extras.getStringArray(COURSE_INFO);

        String currentSection = "User: " + courseInfo[0] + "\n" + "Course Name: " + courseInfo[1] + "\n" + "Course Section: " + courseInfo[2];
        course_data.setText(currentSection);
        addDateProcess();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenSectionInfo(view);
            }
        });

    }

    /**
     * This Method is reached when coming back to this intent, without having to go through the onCreate process all over again
     * No actions are needed at this point
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Upon choosing a specific date, the new layout screen needs some data to be passed.
     * Before passing to a new activity, we record the needed course info in an array.
     * We out the array in an Extra and start the List_of_Attendees Activity.
     * @param view
     */
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

    /**
     * Handles the OnClick of the Take Attendance Button
     * Checks if today's date already exists, if not then create an instance in the DB
     * Create an info string that contains all the details that would be needed for parsing with the Student to take Attendance
     * Add the date to the info string array
     * Put the array in an extra to be passed to the Take_Attendance Activity Intent
     * @param view
     */
    public void TakeAttendance(View view){
        setDate(); //Always set the date before using the global variable
        Thread t = new Thread(new Runnable() {

            @Override
            public void run(){
                if(!dateDao.checkDateExists(courseInfo[0], courseInfo[1], courseInfo[2], date)){ //Checks if today's date already exists, if not then create an instance in the DB

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

    /**
     * This method retrieves the current date, and store it in the date global variable
     */
    private void setDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
    }

    /**
     * Retrieve all dates related to the user/course/section residing in the DB and add them to the
     * datesArray, which is passed to the adapter to be shown in the list view.
     */
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

        /**
         * Handles the OnClick button
         * Goes back to the List_Courses Activity
         */
    }
    public void backToCourses(View view){
        Intent replyIntent = new Intent(this, List_Courses.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }
}


