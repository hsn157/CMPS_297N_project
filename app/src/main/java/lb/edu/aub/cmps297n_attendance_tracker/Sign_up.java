package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import UsersDB.User;
import UsersDB.UserDao;
import UsersDB.UserRoomDatabase;

import static lb.edu.aub.cmps297n_attendance_tracker.Sign_in.USER_LOGIN;

/**
 * This Activity Handles the Sign up procedure
 */
public class Sign_up extends AppCompatActivity {
    public UserRoomDatabase userDataBase; // DB for the users instance
    public UserDao userDao; // Data access objects for users DB
    private EditText username; //For reading the username in the screen's edit text view
    private EditText password; //For reading the password in the screen's edit text view
    private EditText passwordConfirm; //For reading the confirmation password in the screen's edit text view

    /**
     * Bind the concerned layout to the screen
     * initialize the global variable for the users DB
     * initialize the global variable for the usersDao
     * initialize the global variables for 'username', 'password', and 'confirm password' for EditText views
     * Make the 'password' and 'confirm password' hidden
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userDataBase = UserRoomDatabase.getDatabase(getApplicationContext());
        userDao = userDataBase.userDao();

        username = findViewById(R.id.sign_up_user);
        password = findViewById(R.id.sign_up_pass);
        passwordConfirm = findViewById(R.id.sign_up_confirm_pass);

        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }


    /**
     * Handle the 'OnClick' Sign Up button.
     * Assures that no field is empty.
     * Assures that the confirm password field matches the password.
     * Assures that the Username doesn't already exist.
     * If everything is satisfied, add new user to database and shift to the "List_Courses" Intent
     *
     * Toasts a message upon handling any event
     * @param view
     */
    public void signUp(View view) {

        if(username.getText().toString().isEmpty() ||  password.getText().toString().isEmpty() //Assures that no field is empty.
            || passwordConfirm.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(), "Please Fill Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.getText().toString().equals(passwordConfirm.getText().toString())){ //Assures that the confirm password field matches the password.

            Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;

        }


        Toast toast_1 = Toast.makeText(this, "Username already Exists", Toast.LENGTH_SHORT);
        Toast toast_2 = Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, List_Courses.class);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(userDao.checkUserExists(username.getText().toString())){ //Checks if username already exists
                    toast_1.show();
                    return;
                }
                else {
                    User newUser = new User(username.getText().toString(), password.getText().toString());  //If everything is satisfied,
                                                                                                            // add new user to database and
                                                                                                            // shift to the "List_Courses" Intent
                    userDao.insert(newUser);
                    toast_2.show();
                    intent.putExtra(USER_LOGIN, username.getText().toString());
                    startActivity(intent);
                }
            }

        });
    }

    /**
     * Handles the OnClick back button event
     * @param view
     */
    public void backToMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}