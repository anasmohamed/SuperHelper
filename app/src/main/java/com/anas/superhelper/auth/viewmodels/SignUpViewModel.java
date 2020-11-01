package com.anas.superhelper.auth.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.SignUpRepository;

public class SignUpViewModel extends ViewModel {
    LiveData<User> authenticatedUserLiveData;
    LiveData<User> createdUserLiveData;
    private SignUpRepository signUpRepository;

    public SignUpViewModel() {
        super();
        if (signUpRepository == null) {
            signUpRepository = new SignUpRepository();
        }
    }

    public void signUp(User user) {
        authenticatedUserLiveData = signUpRepository.firebaseSignInWithGoogle(user);
    }
    public void uploadIdImage(Bitmap photo,String userEmail) {
         signUpRepository.uploadImage(photo,userEmail);
    }

}
