package com.anas.superhelper.auth.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.anas.superhelper.auth.models.Offer;
import com.anas.superhelper.auth.models.RequestHelper;
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
        requestHelper.setRequestOwnerId(firebaseUser.getUid());
        mRequestsRef.push().setValue(requestHelper);
    }
    public void getSpecificValue(Consumer<String> returnedValue ,String neededString)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String returnedUserType = snapshot.child(neededString).getValue().toString();
                returnedValue.accept(returnedUserType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getSpecificValueFromRequest(Consumer<String> returnedValue ,String neededString,int index)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRequestsRef.child(requestsKeysList.get(index)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String returnedUserType = snapshot.child(neededString).getValue().toString();
                returnedValue.accept(returnedUserType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void insertOffer(Offer offer,int index)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mRequestsRef.child(requestsKeysList.get(index)).child("Offers").push().setValue(offer);

    }
    public void getRequests(Consumer<List<RequestHelper>> listConsumer,Consumer <List<String>> keyList) {
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
                         List<String> requestsKeysList = new ArrayList<>();
                         snapshot.getChildren().forEach(dataSnapshot1 -> {
                             Log.i("snashotkey",dataSnapshot1.getKey())  ;

                             requestsKeysList.add(dataSnapshot1.getKey());

                         });
                         snapshot.getChildren().forEach(dataSnapshot ->
                                 list.add(dataSnapshot.getValue(RequestHelper.class)));
                         listConsumer.accept(list);
keyList.accept(requestsKeysList);
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
