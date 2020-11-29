package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Course_Info extends AppCompatActivity {
    String[] ClassSectionsArray ={"section1","section2","section3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ClassSectionsArray);

        ListView listView = (ListView) findViewById(R.id.course_sections);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenSectionInfo(view);
            }
        });

    }
    public void OpenSectionInfo(View view){
        Intent intent = new Intent(this, Section_Information.class);
        startActivity(intent);

    }
}