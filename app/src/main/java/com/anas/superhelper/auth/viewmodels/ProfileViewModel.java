package com.anas.superhelper.auth.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.LoginRepository;
import com.anas.superhelper.auth.repository.ProfileRepository;

import java.util.function.Consumer;

public class ProfileViewModel extends ViewModel {

    private ProfileRepository profileRepository;

    public LiveData<User> authenticatedUserLiveData;

    public ProfileViewModel() {
        super();
        if(profileRepository == null){
            profileRepository = new ProfileRepository();
        }
    }
  public void getUser(Consumer<User> returnValueResult){
        profileRepository.getUser(returnValueResult);
  }
  public void updateProfileImage(Bitmap profileBitmap){
        profileRepository.uploadProfileImage(profileBitmap);
  }

}
