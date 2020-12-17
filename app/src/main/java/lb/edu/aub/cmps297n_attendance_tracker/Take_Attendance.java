package lb.edu.aub.cmps297n_attendance_tracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import StudentsDB.Student;
import StudentsDB.StudentDao;
import StudentsDB.StudentDatabase;

import java.util.Calendar;


import static lb.edu.aub.cmps297n_attendance_tracker.Section_Information.COURSE_DATE;
import static lb.edu.aub.cmps297n_attendance_tracker.Take_Manual_Attendance.STUDENT_ID;
import static lb.edu.aub.cmps297n_attendance_tracker.Take_Manual_Attendance.STUDENT_NAME;

public class Take_Attendance extends AppCompatActivity {

    public static int MANUAL_ENTRY = 1;
    String[] studentInfo;
    StudentDao studentDao;
    StudentDatabase studentDatabase;
    Button scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        studentInfo = new String[6];
        final Bundle extras = getIntent().getExtras();
        String temp[] = extras.getStringArray(COURSE_DATE);
        for(int i=0; i<4; i++) {
            studentInfo[i] = temp[i];
        }

        studentDatabase = StudentDatabase.getDatabase(getApplicationContext());
        studentDao = studentDatabase.studentDao();
        scan = findViewById(R.id.capture_id_photo);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MANUAL_ENTRY){
            studentInfo[4] = data.getExtras().getString(STUDENT_NAME);
            studentInfo[5] = Integer.toString(data.getExtras().getInt(STUDENT_ID));

            addStudent();

        }
        else{

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if(result != null){

                if(result.getContents() != null){

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    String[] content = result.getContents().split(System.getProperty("line.separator"));
                    studentInfo[4] = content[0];
                    studentInfo[5] = content[1];
                    String show_data = "ID: " + content[0] + "\n" + "Name: " + content[1];
                    builder.setMessage(show_data);
                    builder.setTitle("Scanning Result");
                    builder.setPositiveButton("Record Student", new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            addStudent();
                            dialog.dismiss();
                        }
                    }).setNegativeButton( "Discard & Scan Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    scanBarcode(scan);
                                }
                            }).setNeutralButton("Discard",  new DialogInterface.OnClickListener() {
                                  @Override
                                 public void onClick(DialogInterface dialog, int which) {

                                      dialog.dismiss();
                                  }
                            });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
            }
        }

    }

    public void ManualEntry(View view){
        Intent intent = new Intent(this,Take_Manual_Attendance.class);
        startActivityForResult(intent, MANUAL_ENTRY);
    }

    public void backToDates(View view){
        Intent replyIntent = new Intent(this, Section_Information.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    public void scanBarcode(View view){
        IntentIntegrator integrator = new IntentIntegrator( this);
        integrator.setCaptureActivity(Capture_Id.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    private void addStudent(){
        Toast t1 = Toast.makeText(this, "Student Already Recorded", Toast.LENGTH_LONG);
        Toast t2 = Toast.makeText(this, "Student Recorded Successfully", Toast.LENGTH_LONG);

        Thread t = new Thread(new Runnable(){

            @Override public void run(){
                if(studentDao.checkStudentExists(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3], Integer.parseInt(studentInfo[5]))){
                    t1.show();
                }
                else{
                    Calendar rightNow = Calendar.getInstance();
                    int minutes = rightNow.get(Calendar.MINUTE);

                    int hours = rightNow.get(Calendar.HOUR);
                    String am_pm = "PM";
                    if(rightNow.get(Calendar.AM_PM) == 0){
                        am_pm = "AM";
                    }
                    String time = hours + ":" + minutes + " " + am_pm;
                    studentDao.insert(new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3], studentInfo[4], Integer.parseInt(studentInfo[5]), time));
                    t2.show();
                }
            }
        });

        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}