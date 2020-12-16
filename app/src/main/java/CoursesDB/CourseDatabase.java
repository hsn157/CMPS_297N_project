package CoursesDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Course.class}, version = 1, exportSchema = false)
public abstract class CourseDatabase extends RoomDatabase{
    public abstract CourseDao courseDao();

    public static CoursesDB.CourseDatabase INSTANCE;
    /*
    private static final int NUMBER_OF_THREADS = 3;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

     */

    public static  CoursesDB.CourseDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CoursesDB.CourseDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CoursesDB.CourseDatabase.class, "courses_table")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

