package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * The Attendance Tracker App is expected to be downloaded on a shared device in each classroom.
 *
 * The professors/lecturers are expected to sign-in/up for an account before using the app.
 *
 * In general, the Database for each account is expected to be held in some back-end, but as
 * communicating with a back-end server to store accounts and attendees info, we'll simulate
 * this using the device's local storage as we will be applying what we learnt about data persistence
 * using Room DB architecture.
 *
 * This MainActivity simply contains the Sign-In and Sign-Up actions, as the control would be
 * transferred via Intents
 */
public class MainActivity extends AppCompatActivity {


    /**
     * Binding the needed layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Method to run the "OnClick" event for the Sign up button
     * @param view
     */
    public void SignUP(View view) {
        Intent intent = new Intent(this, Sign_up.class);
        startActivity(intent);
    }

    /**
     * Method to run the "OnClick" event for the Sign in button
     * @param view
     */
    public void SignIN(View view) {
        Intent intent = new Intent(this, Sign_in.class);
        startActivity(intent);
    }
}