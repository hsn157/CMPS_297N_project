package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import UsersDB.UserDao;
import UsersDB.UserRoomDatabase;

public class Sign_in extends AppCompatActivity {

    UserDao userDao; // Data access objects for users DB
    UserRoomDatabase userDataBase; // DB for the users instance
    private EditText username; //For reading the username in the screen's edit text view
    private EditText password; //For reading the password in the screen's edit text view

    public static final String USER_LOGIN = "username"; //Extra id to be passed for the username to the next Activity

    /**
     * Bind the concerned layout to the screen
     * initialize the global variable for the users DB
     * initialize the global variable for the usersDao
     * initialize the global variables for 'username', 'password' for EditText views
     * Make the 'password' invisible
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userDataBase = UserRoomDatabase.getDatabase(getApplicationContext());
        userDao = userDataBase.userDao();

        username = findViewById(R.id.sign_in_email);
        password = findViewById(R.id.sign_in_pass);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /**
     * Checks if all fields are filled
     * Checks if user name exists or not in the DB
     * Check if the password is correct as in the DB
     * Check if the password is correct as in the DB
     *
     * If everything is satisfied, start a new Activity 'Course_list', passing the username as an extra,
     * so that the new Activity would be able to retrieve the needed data (courses, dates, student, etc.) for this username
     * @param view
     */
    public void confirmSignIn (View view){
        if(username.getText().toString().isEmpty() ||  password.getText().toString().isEmpty()) { // Checks if all fields are filled

            Toast.makeText(getApplicationContext(), "Please Fill Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast toast_1 = Toast.makeText(this, "Username doesn't Exists", Toast.LENGTH_SHORT);
        Toast toast_2 = Toast.makeText(this, "Successfully Signed In", Toast.LENGTH_SHORT);
        Toast toast_3 = Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, List_Courses.class);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(!userDao.checkUserExists(username.getText().toString())){ //Checks if user name exists or not in the DB
                    toast_1.show();
                    return;
                }

                if(userDao.checkPassword(username.getText().toString(), password.getText().toString())){ //Check if the password is correct as in the DB
                    toast_2.show();
                    intent.putExtra(USER_LOGIN, username.getText().toString());
                    startActivity(intent);
                }

                else { //If the password is incorrect, toast a message and return
                    toast_3.show();
                    return;
                }
            }
        });
    }

    /**
     * Handles the OnClick of the 'back button', starts the MainActivity Activity
     * @param view
     */
    public void backToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}