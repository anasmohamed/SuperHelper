package com.anas.superhelper.auth.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.SignUpRepository;
import com.google.firebase.auth.AuthCredential;

public class SignUpViewModel extends AndroidViewModel {
    private SignUpRepository signUpRepository;
    LiveData<User> authenticatedUserLiveData;
    LiveData<User> createdUserLiveData;

    public SignUpViewModel(Application application) {
        super(application);
        signUpRepository = new SignUpRepository();
    }

    void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = signUpRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    void createUser(User authenticatedUser) {
       // createdUserLiveData = signUpRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }
}
