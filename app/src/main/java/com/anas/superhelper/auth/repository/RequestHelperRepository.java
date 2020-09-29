package com.anas.superhelper.auth.repository;

import androidx.lifecycle.MutableLiveData;

import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestHelperRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Users");
    public void insertHelperRequestData(RequestHelper requestHelper) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRef.child(firebaseUser.getUid()).child("requests").push().setValue(requestHelper);

    }
}
