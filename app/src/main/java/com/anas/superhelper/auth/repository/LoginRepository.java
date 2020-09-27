package com.anas.superhelper.auth.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anas.superhelper.auth.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Users");

    public MutableLiveData<User> firebaseSignIn(String email, String password) {
        MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                getUser(authenticatedUserMutableLiveData::setValue);

            } else {
                authTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.i("repo firebase", "onFailure: " + e.getMessage());
                    }
                });
            }
        });
        return authenticatedUserMutableLiveData;
    }

    public void getUser(ReturnValueResult returnValueResult) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();
                user.setFirstName(dataSnapshot.child("firstName").getValue(String.class));
                user.setLastName(dataSnapshot.child("lastName").getValue(String.class));
                user.setGender(dataSnapshot.child("gender").getValue(String.class));
                user.setPhone(dataSnapshot.child("phone").getValue(String.class));
                user.setDate(dataSnapshot.child("date").getValue(String.class));
                user.setEmail(dataSnapshot.child("email").getValue(String.class));
                returnValueResult.onResult(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public interface ReturnValueResult {
        void onResult(User user);
    }
}
