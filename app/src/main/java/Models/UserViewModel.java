package Models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {

    private MutableLiveData<User_Intelik> user;
    public LiveData<User_Intelik> getUser() {
        if (user == null) {
            user = new MutableLiveData<User_Intelik>();
            loadUser();
        }
        return user;
    }


    private void loadUser() {
        // Do an asynchronous operation to fetch users.
    }



    /*
    private MutableLiveData<List<User_Intelik>> users;
    public LiveData<List<User_Intelik>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<List<User_Intelik>>();
            loadUsers();
        }
        return users;
    }


    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }

    */

}
