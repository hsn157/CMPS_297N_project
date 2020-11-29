package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class List_of_Attendees extends AppCompatActivity {

    String[] ClassAttendeesList = {"ClassAttendee1","ClassAttendee2","ClassAttendee3","ClassAttendee4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_attendees);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, ClassAttendeesList);

        ListView listView = (ListView) findViewById(R.id.attendees_list);
        listView.setAdapter(adapter);
    }
}