package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List_Courses extends AppCompatActivity {

    String[] myClassesStringArray = {"Class1","Class2","Class3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_courses);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myClassesStringArray);

        ListView listView = (ListView) findViewById(R.id.course_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenCourseInfo(view);
            }
        });
    }

    public void OpenCourseInfo(View view){
        Intent intent = new Intent(this, Course_Info.class);
        startActivity(intent);
    }

    public void AddNewCourse(View view){
        Intent intent = new Intent(this, Add_a_Course.class);
        startActivity(intent);
    }
}