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

public class Sign_up extends AppCompatActivity {
    public UserRoomDatabase userDataBase;
    public UserDao userDao;
    private EditText username;
    private EditText password;
    private EditText passwordConfirm;

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
    


    public void signUp(View view) {

        if(username.getText().toString().isEmpty() ||  password.getText().toString().isEmpty()
            || passwordConfirm.getText().toString().isEmpty()){

            Toast.makeText(getApplicationContext(), "Please Fill Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.getText().toString().equals(passwordConfirm.getText().toString())){

            Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
            return;

        }


        Toast toast_1 = Toast.makeText(this, "Username already Exists", Toast.LENGTH_SHORT);
        Toast toast_2 = Toast.makeText(this, "Successfully Signed Up", Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, List_Courses.class);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(userDao.checkUserExists(username.getText().toString())){
                    toast_1.show();
                    return;
                }
                else {
                    User newUser = new User(username.getText().toString(), password.getText().toString());
                    userDao.insert(newUser);
                    toast_2.show();
                    intent.putExtra(USER_LOGIN, username.getText().toString());
                    startActivity(intent);
                }
            }

        });
    }
}