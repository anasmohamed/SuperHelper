package com.anas.superhelper.auth.viewmodels;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.anas.superhelper.auth.models.User;
import com.anas.superhelper.auth.repository.ProfileRepository;

import java.util.function.Consumer;

public class ProfileViewModel extends ViewModel {

    public LiveData<User> authenticatedUserLiveData;
    private ProfileRepository profileRepository;

    public ProfileViewModel() {
        super();
        if (profileRepository == null) {
            profileRepository = new ProfileRepository();
        }
    }

    public void getUser(Consumer<User> returnValueResult) {
        profileRepository.getUser(returnValueResult);
    }

    public void updateProfileImage(Bitmap profileBitmap) {
        profileRepository.uploadProfileImage(profileBitmap);
    }

    public void updateUserProfileData(String valueName, String value) {
        profileRepository.updateUserProfileData(valueName, value);
    }
}
