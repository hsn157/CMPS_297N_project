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

    public static int MANUAL_ENTRY = 1; //Request Code to register student manually
    String[] studentInfo; //Student info to be retrieved from extras
    StudentDao studentDao; //Student Dao to do insertions/queries
    StudentDatabase studentDatabase; //Database instance
    Button scan; //Button to reference the barcode scanning button

    /**
     * Initialize the Layout, database, dao, scan button
     * retrieve the course's info from the extras, to be added to studentInfo Array, that will be needed
     * when adding students, as these info will be stored in the DB of the student, in order to properly
     * retrieve them when needed later on
     * @param savedInstanceState
     */
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

    /**
     * check if this method was called due to a manual request entry for th student, then add the student after retrieving his name and id from extras
     * Otherwise it is a barcode scanning request.
     * Assure that the Activity Result returned some result from the barcode.
     *
     * Simple alert message to show the user was did the barcode scan - outcomes, and set of
     * options which are: record student, discard the result and scan again, or just discard the result.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MANUAL_ENTRY) { //check if this method was called due to a manual request entry for th student
            if(resultCode == RESULT_OK) {
                studentInfo[4] = data.getExtras().getString(STUDENT_NAME);
                studentInfo[5] = Integer.toString(data.getExtras().getInt(STUDENT_ID));

                addStudent();
            }
        }
        else{ //Otherwise it is a barcode scanning request

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

            if(result != null){ //Assure that the Activity Result returned some result from the barcode

                if(result.getContents() != null){ //Assure that the barcode was able to trigger some result

                    //Simple alert message to show the user was did the barcode scan - outcomes, and set of
                    //options which are: record student, discard the result and scan again, or just discard the result

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

    /**
     * Handles the OnClick of the take Manual Attendance button
     * Starts the concerned activity 'Take Manual Attendance'
     * @param view
     */
    public void ManualEntry(View view){
        Intent intent = new Intent(this,Take_Manual_Attendance.class);
        startActivityForResult(intent, MANUAL_ENTRY);
    }

    /**
     * Handles the back button, in order to back to the dates
     * @param view
     */
    public void backToDates(View view){
        Intent replyIntent = new Intent(this, Section_Information.class);
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    /**
     * Handles the OnClick button of the Scan Barcode Button
     * Starts a Capture_Id Activity which is responsible for scanning and reading the barcode
     * initialize several setting for the scanning environment
     * @param view
     */
    public void scanBarcode(View view){
        IntentIntegrator integrator = new IntentIntegrator( this);
        integrator.setCaptureActivity(Capture_Id.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    /**
     * Add Student to the DB
     * Checks if the student to-be recorded is already recorded
     * if not, then attempt to add it using the dao insert method
     */
    private void addStudent(){
        Toast t1 = Toast.makeText(this, "Student Already Recorded", Toast.LENGTH_LONG);
        Toast t2 = Toast.makeText(this, "Student Recorded Successfully", Toast.LENGTH_LONG);

        Thread t = new Thread(new Runnable(){

            @Override public void run(){
                if(studentDao.checkStudentExists(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3], Integer.parseInt(studentInfo[5]))){ //Checks if the student to-be recorded is already recorded
                    t1.show();
                }
                else{ //Get the current time as HH:MM am/pm format
                    Calendar rightNow = Calendar.getInstance();
                    int minutes = rightNow.get(Calendar.MINUTE);

                    int hours = rightNow.get(Calendar.HOUR);
                    String am_pm = "PM";
                    if(rightNow.get(Calendar.AM_PM) == 0){
                        am_pm = "AM";
                    }

                    studentDao.insert(new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3], studentInfo[4], Integer.parseInt(studentInfo[5]), hours, minutes, am_pm));
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