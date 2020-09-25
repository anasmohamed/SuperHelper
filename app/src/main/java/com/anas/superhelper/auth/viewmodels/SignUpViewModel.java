package com.anas.superhelper.auth.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.SignUpRepository;
import com.google.firebase.auth.AuthCredential;

public class SignUpViewModel extends ViewModel {
    private SignUpRepository signUpRepository;
    LiveData<User> authenticatedUserLiveData;
    LiveData<User> createdUserLiveData;

    public SignUpViewModel() {
        super();
        if(signUpRepository == null){
            signUpRepository = new SignUpRepository();
        }
    }

  public   void signUp(User user) {
        authenticatedUserLiveData = signUpRepository.firebaseSignInWithGoogle(user);
    }


}
