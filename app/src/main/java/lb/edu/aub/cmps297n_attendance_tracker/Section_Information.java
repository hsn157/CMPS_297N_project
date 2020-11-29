package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Section_Information extends AppCompatActivity {


    String[] SectionDatesArrat = {"Date1","Date2","Date3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_information);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, SectionDatesArrat);

        ListView listView = (ListView) findViewById(R.id.dates_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenSectionInfo(view);
            }
        });

    }
    public void OpenSectionInfo(View view){
        Intent intent = new Intent(this, List_of_Attendees.class);
        startActivity(intent);

    }

    public void TakeAttendance(View view){
        Intent intent = new Intent(this, Take_Attendance.class);
        startActivity(intent);

    }
}
