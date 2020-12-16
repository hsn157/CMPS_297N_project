package DatesDB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Date.class}, version = 1, exportSchema = false)
public abstract class DateDatabase extends RoomDatabase{
    public abstract DateDao dateDao();

    public static DateDatabase INSTANCE;
    /*
    private static final int NUMBER_OF_THREADS = 3;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

     */

    public static DateDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DatesDB.DateDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DateDatabase.class, "dates_table")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

