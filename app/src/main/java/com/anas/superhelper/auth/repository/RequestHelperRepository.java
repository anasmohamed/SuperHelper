package com.anas.superhelper.auth.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anas.superhelper.auth.models.RequestHelper;
import com.anas.superhelper.auth.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RequestHelperRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Users");
    DatabaseReference mRequestsRef = database.getReference().child("Requests");

    public void insertHelperRequestData(RequestHelper requestHelper) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRef.child(firebaseUser.getUid()).child("requests").push().setValue(requestHelper);
        mRequestsRef.push().setValue(requestHelper);
    }
    public void getUserType(Consumer<String> userType)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String returnedUserType = snapshot.child("userType").getValue().toString();
                userType.accept(returnedUserType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getRequests(Consumer<List<RequestHelper>> listConsumer) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             Log.i("dataUserType",dataSnapshot.child("userType").getValue().toString());
             if(dataSnapshot.child("userType").getValue().toString().equalsIgnoreCase("helper"))
             {
                 mRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         List<RequestHelper> list = new ArrayList<>();
                         snapshot.getChildren().forEach(dataSnapshot -> list.add(dataSnapshot.getValue(RequestHelper.class)));
                         listConsumer.accept(list);
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });

             }else {
                 mRef.child(firebaseUser.getUid()).child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         List<RequestHelper> list = new ArrayList<>();
                         snapshot.getChildren().forEach(dataSnapshot -> list.add(dataSnapshot.getValue(RequestHelper.class)));
                         listConsumer.accept(list);
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("firebase myerror",databaseError.getMessage());
            }
        });



    }
}
