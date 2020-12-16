package UsersDB;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel{

    private UserRepository mRepository;


    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new UserRepository(application);

    }

    //public LiveData<List<User>> getUser(String username) {
       // return mRepository.getUser(username);
   // }

    public boolean checkPassword(String username, String password) { return mRepository.checkPassword(username, password);}
    public void insert(User user) {
        mRepository.insert(user);
    }
}
