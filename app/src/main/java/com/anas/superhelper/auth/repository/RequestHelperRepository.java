package com.anas.superhelper.auth.repository;

import com.anas.superhelper.auth.models.RequestHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestHelperRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Users");
    public void insertHelperRequestData() {
       // RequestHelper requestHelper = new RequestHelper();

      //  mRef.child("users").child(userId).setValue(user);
    }
}
