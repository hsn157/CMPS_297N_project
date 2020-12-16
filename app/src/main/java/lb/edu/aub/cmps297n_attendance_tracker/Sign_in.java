package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import UsersDB.User;
import UsersDB.UserDao;
import UsersDB.UserRoomDatabase;
import UsersDB.UserViewModel;

public class Sign_in extends AppCompatActivity {

    UserDao userDao;
    UserRoomDatabase userDataBase;
    private EditText username;
    private EditText password;

    public static final String USER_LOGIN = "username";
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

    public void confirmSignIn (View view){
        if(username.getText().toString().isEmpty() ||  password.getText().toString().isEmpty()) {

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

                if(!userDao.checkUserExists(username.getText().toString())){
                    toast_1.show();
                    return;
                }

                if(userDao.checkPassword(username.getText().toString(), password.getText().toString())){
                    toast_2.show();
                    intent.putExtra(USER_LOGIN, username.getText().toString());
                    startActivity(intent);
                }

                else {
                    toast_3.show();
                    return;
                }
            }
        });
    }
}