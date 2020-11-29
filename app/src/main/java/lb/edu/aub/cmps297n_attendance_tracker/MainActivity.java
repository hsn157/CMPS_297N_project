package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void SignUP(View view) {
        Intent intent = new Intent(this, Sign_up.class);
        startActivity(intent);
    }
    public void SignIN(View view) {
        Intent intent = new Intent(this, Sign_in.class);
        startActivity(intent);
    }
}