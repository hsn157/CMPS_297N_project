package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import CoursesDB.Course;
import CoursesDB.CourseDao;
import CoursesDB.CourseDatabase;

import static lb.edu.aub.cmps297n_attendance_tracker.Add_a_Course.REPLY_COURSE_NAME;
import static lb.edu.aub.cmps297n_attendance_tracker.Add_a_Course.REPLY_COURSE_SECTION;
import static lb.edu.aub.cmps297n_attendance_tracker.Sign_in.USER_LOGIN;

public class List_Courses extends AppCompatActivity {

    List<Course> coursesList; //List to store the retrieved courses
    String[] coursesNames; //Stores the courses names for the List View adapter
    CourseDao courseDao; //Data access objects instance for insertions/queries
    CourseDatabase courseDatabase; //Global Variable holding the DB instance
    String username; //username of the concerned user
    ArrayAdapter<String> adapter; //Adapter for data transfer to the list view
    ListView listView; //List view referring to the screen's instance
    public static final int ADD_COURSE_REQUEST_CODE = 1; //Adding course intent request code
    public static final int PROCEED_TO_COURSE = 2; //Proceeding to course Info code

    public static final String COURSE_INFO = "course_info"; //Id string for the course info extra

    /**
     * Initialize the listView variable to the id of the view in the screen
     * Initializing the Dao
     * Initializing the DB
     * reading the username from the bundle
     * Calling the 'adCourseProcess' which gets all courses for this specific username
     * Setting a click listener for the listView cells
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_courses);
        listView = (ListView) findViewById(R.id.course_list_view);

        courseDatabase = CourseDatabase.getDatabase(getApplicationContext());
        courseDao = courseDatabase.courseDao();

        final Bundle extras = getIntent().getExtras();
        username = extras.getString(USER_LOGIN, "");

        addCourseProcess();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenCourseInfo(position);
            }
        });
    }

    /**
     * Assuring which Activity replied - Add_a_course.
     * Assure the reply - resultCode- was true, in ordered to call the insert method in the Dao.
     * Assure that the course-section pair doesn't already exist.
     * if all conditions are satisfied, the insert method is called in a thread.
     * Toast messages prints which conditional branch was traversed
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST_CODE) { //Assuring which intent replied - Add_a_course
            if (resultCode == RESULT_OK) { //Assure the reply - resultCode- was true, in ordered to call the insert method in the Dao
                Course course = new Course(username, data.getStringExtra(REPLY_COURSE_NAME), data.getStringExtra(REPLY_COURSE_SECTION));
                Toast t2 = Toast.makeText(this, "Course Successfully added", Toast.LENGTH_LONG);
                Toast t1 = Toast.makeText(this, "Course Section Already Exists", Toast.LENGTH_LONG);

                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (courseDao.checkCourseExists(course.getUsername(), course.getCourse_name(), course.getCourse_section())) {
                            t1.show(); //Make sure the course-section pair doesn't already exist
                        } else {
                            courseDao.insert(course);
                            t2.show();
                        }
                    }
                });

                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addCourseProcess();
            } else {
                Toast.makeText(this, "No course was added", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Handles the clicks on any of the List view cells.
     * Parsing data into a strings array and passing it as an extra to the next intent:
     * course[0] = username, course[1] = course_name, course[2] = course_section
     * @param position
     */
    public void OpenCourseInfo(int position) {
        String[] course = parse_data(position);
        course[0] = username;
        Intent intent = new Intent(this, Section_Information.class);
        intent.putExtra(COURSE_INFO, course);
        startActivityForResult(intent, PROCEED_TO_COURSE);
    }

    /**
     * This method reads the text of the "clicked" cell and parse it
     * The needed info are the course_name and the course_section
     * @param position
     * @return
     */
    private String[] parse_data(int position) {
        String[] my_data = new String[3];

        String view_string = (String) listView.getItemAtPosition(position);
        my_data[1] = view_string.substring(0, view_string.indexOf(", Section: "));
        int beg = view_string.indexOf(", Section: ") + 11;
        my_data[2] = view_string.substring(beg, view_string.length());

        return my_data;
    }

    /**
     * Starts a new Activity for adding new course
     * @param view
     */
    public void AddNewCourse(View view) {
        Intent intent = new Intent(this, Add_a_Course.class);
        startActivityForResult(intent, ADD_COURSE_REQUEST_CODE);
    }

    private void addCourseProcess() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                coursesList = courseDao.getAllCourses(username);
                coursesNames = new String[coursesList.size()];

                for (int i = 0; i < coursesList.size(); i++) { //The loops all the courses in the list, and parse them to be view as expected in the list view

                    coursesNames[i] = coursesList.get(i).getCourse_name() + ", Section: " + coursesList.get(i).getCourse_section();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Bind the courses names to the adapter
        adapter = new ArrayAdapter<String>(List_Courses.this,
                android.R.layout.simple_list_item_1, coursesNames);
        listView.setAdapter(adapter);
    }

    /**
     * Handle the back button to Main Menu
     * @param view
     */
    public void backToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

