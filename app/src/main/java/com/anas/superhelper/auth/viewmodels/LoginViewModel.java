package com.anas.superhelper.auth.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.LoginRepository;
import com.anas.superhelper.auth.repository.SignUpRepository;
import com.anas.superhelper.auth.view.LoginActivity;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;
     public  LiveData<User> authenticatedUserLiveData;

    public LoginViewModel() {
        super();
        if(loginRepository == null){
            loginRepository = new LoginRepository();
        }
    }

    public void login(String email, String password) {
        authenticatedUserLiveData = loginRepository.firebaseSignIn(email,password);
    }
}
