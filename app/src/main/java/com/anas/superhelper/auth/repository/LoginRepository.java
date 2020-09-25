package com.anas.superhelper.auth.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.anas.superhelper.auth.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database =  FirebaseDatabase.getInstance();
    DatabaseReference mRef =  database.getReference().child("Users");
    private LoginStatusInterface loginStatusInterface;
    public MutableLiveData<User> firebaseSignIn(String email,String password) {
        MutableLiveData<User> authenticatedUserMutableLiveData = new MutableLiveData<>();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                User user = new User();
                user.setFirstName(mRef.child(firebaseUser.getUid()).child("firstName").toString());
                user.setLastName(mRef.child(firebaseUser.getUid()).child("lastName").toString());
                user.setGender(mRef.child(firebaseUser.getUid()).child("gender").toString());
                user.setPhone(mRef.child(firebaseUser.getUid()).child("phone").toString());
                user.setDate(mRef.child(firebaseUser.getUid()).child("date").toString());
                user.setEmail(mRef.child(firebaseUser.getUid()).child("email").toString());
                authenticatedUserMutableLiveData.setValue(user);

            } else {
                authTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginStatusInterface.onLoginFail();

                        Log.i("repo firebase", "onFailure: "+e.getMessage());
                    }
                });
            }
        });
        return authenticatedUserMutableLiveData;
    }
}
