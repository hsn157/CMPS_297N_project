package UsersDB;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private LiveData<List<User>> users;
   // priavte LiveData<> mUsers;
    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();

    }
    /*
    public LiveData<List<User>> getUser(String username){
        users = mUserDao.getUser(username);
        return users;

    }

     */
/*
    User[] getUser(String username){

          return mUserDao.getUser(username);
    }


 */
    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public boolean checkPassword(String username, String password){
        return mUserDao.checkPassword(username, password);
    }


    /*

    private static class getAsyncTask extends AsyncTask<String, Void, User> {

        private UserDao mAsyncTaskDao;

        getAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected User doInBackground(final String... params) {
            return mAsyncTaskDao.getUser(params[0]);

        }
    }

     */
}
