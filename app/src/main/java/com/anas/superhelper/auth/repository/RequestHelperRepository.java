package com.anas.superhelper.auth.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

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
        String mGroupId = mRequestsRef.push().getKey();

        requestHelper.setSenderId(firebaseUser.getUid());
        mRef.child(firebaseUser.getUid()).child("requests").child(mGroupId).setValue(requestHelper);
        mRequestsRef.child(mGroupId).setValue(requestHelper);


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
    public void getSpecificValueFromRequest(Consumer<String> returnedValue ,String neededString,String key)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mRequestsRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()){
                String returnedUserType = snapshot.child(neededString).getValue().toString();
                returnedValue.accept(returnedUserType);}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getSpecificValueFromOffers(String requestKey,String offerKey,String neededValue,Consumer<String> returnedValue){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("dataUserType",dataSnapshot.child("userType").getValue().toString());
                if(dataSnapshot.child("userType").getValue().toString().equalsIgnoreCase("helper"))
                {
                    mRequestsRef.child(requestKey).child("Offers").child(offerKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()){
                                String returnedUserType = snapshot.child(neededValue).getValue().toString();
                                returnedValue.accept(returnedUserType);}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    mRef.child(firebaseUser.getUid()).child("requests").child(requestKey).child("Offers").child(offerKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()){
                                String returnedUserType = snapshot.child(neededValue).getValue().toString();
                                returnedValue.accept(returnedUserType);}
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
    public void getOffers(String key,Consumer<List<Offer>> offersList)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("dataUserType",dataSnapshot.child("userType").getValue().toString());
                if(dataSnapshot.child("userType").getValue().toString().equalsIgnoreCase("helper"))
                {
                    mRequestsRef.child(key).child("Offers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Offer> list = new ArrayList<>();

                            snapshot.getChildren().forEach(dataSnapshot ->
                                    list.add(dataSnapshot.getValue(Offer.class)));
                            offersList.accept(list);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    mRef.child(firebaseUser.getUid()).child("requests").child(key).child("Offers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<Offer> list = new ArrayList<>();
                            snapshot.getChildren().forEach(dataSnapshot -> list.add(dataSnapshot.getValue(Offer.class)));
                            offersList.accept(list);
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
    public void updateOfferStatus(String requestKey,List offersKeys,String thisOfferKey)
    {        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        for (int i = 0 ;i < offersKeys.size();i++){
           if (offersKeys.get(i) == thisOfferKey) {
               mRequestsRef.child(requestKey).child("Offers").child(offersKeys.get(i).toString()).child("status").setValue("accept");
               mRef.child(firebaseUser.getUid()).child("requests").child(requestKey).child("Offers").child(offersKeys.get(i).toString()).child("status").setValue("تم قبول عرضك");

           }else {
               mRequestsRef.child(requestKey).child("Offers").child(offersKeys.get(i).toString()).child("status").setValue("closed");
               mRef.child(firebaseUser.getUid()).child("requests").child(requestKey).child("Offers").child(offersKeys.get(i).toString()).child("status").setValue("مغلق");

           }
       }

    }
    public void getOffersKeys(String requestKey,Consumer<List<String>> returnedOffersKeys)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("dataUserType",dataSnapshot.child("userType").getValue().toString());
                if(dataSnapshot.child("userType").getValue().toString().equalsIgnoreCase("helper"))
                {
                    mRequestsRef.child(requestKey).child("Offers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> list = new ArrayList<>();

                            snapshot.getChildren().forEach(dataSnapshot ->
                                    list.add(dataSnapshot.getKey()));
                            returnedOffersKeys.accept(list);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    mRef.child(firebaseUser.getUid()).child("requests").child(requestKey).child("Offers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> list = new ArrayList<>();
                            snapshot.getChildren().forEach(dataSnapshot -> list.add(dataSnapshot.getKey()));
                            returnedOffersKeys.accept(list);
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
    public void insertOffer(Offer offer,String key)
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String mGroupId = mRequestsRef.push().getKey();

        mRequestsRef.child(key).child("Offers").child(mGroupId).setValue(offer);
        mRef.child(firebaseUser.getUid()).child("requests").child(key).child("Offers").child(mGroupId).setValue(offer);
        Log.i("reciverid",offer.getReceiver());
        mRef.child(offer.getReceiver()).child("requests").child(key).child("Offers").child(mGroupId).setValue(offer);

    }
    public void getRequests(Consumer<List<RequestHelper>> listConsumer) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        mRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             if(dataSnapshot.child("userType").getValue().toString().equalsIgnoreCase("helper"))
             {
                 mRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         List<RequestHelper> list = new ArrayList<>();

                         snapshot.getChildren().forEach(dataSnapshot ->
                                 list.add(dataSnapshot.getValue(RequestHelper.class)));
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
    public void getKeysList(Consumer<List<String>> keysList) {
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
                            List<String> list = new ArrayList<>();

                            snapshot.getChildren().forEach(dataSnapshot ->
                                    list.add(dataSnapshot.getKey()));
                            keysList.accept(list);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    mRef.child(firebaseUser.getUid()).child("requests").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> list = new ArrayList<>();
                            snapshot.getChildren().forEach(dataSnapshot -> list.add(dataSnapshot.getKey()));
                            keysList.accept(list);
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
