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

    List<Course> coursesList;
    String[] coursesNames;
    CourseDao courseDao;
    CourseDatabase courseDatabase;
    String username;
    ArrayAdapter<String> adapter;
    ListView listView;
    public static final int ADD_COURSE_REQUEST_CODE = 1;
    public static final int PROCEED_TO_COURSE = 2;

    public static final String COURSE_INFO = "course_info";

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Course course = new Course(username, data.getStringExtra(REPLY_COURSE_NAME), data.getStringExtra(REPLY_COURSE_SECTION));
                Toast t2 = Toast.makeText(this, "Course Successfully added", Toast.LENGTH_LONG);
                Toast t1 = Toast.makeText(this, "Course Section Already Exists", Toast.LENGTH_LONG);

                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (courseDao.checkCourseExists(course.getUsername(), course.getCourse_name(), course.getCourse_section())) {
                            t1.show();
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

    public void OpenCourseInfo(int position) {
        String[] course = parse_data(position);
        course[0] = username;
        Intent intent = new Intent(this, Section_Information.class);
        intent.putExtra(COURSE_INFO, course);
        startActivityForResult(intent, PROCEED_TO_COURSE);
    }

    private String[] parse_data(int position) {
        String[] my_data = new String[3];

        String view_string = (String) listView.getItemAtPosition(position);
        my_data[1] = view_string.substring(0, view_string.indexOf(", Section: "));
        int beg = view_string.indexOf(", Section: ") + 11;
        my_data[2] = view_string.substring(beg, view_string.length());

        return my_data;
    }

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

                for (int i = 0; i < coursesList.size(); i++) {

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

        adapter = new ArrayAdapter<String>(List_Courses.this,
                android.R.layout.simple_list_item_1, coursesNames);
        listView.setAdapter(adapter);
    }

    public void signOut(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

}

