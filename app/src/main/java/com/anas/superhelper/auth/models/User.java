package com.anas.superhelper.auth.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Serializable, Parcelable {
    boolean isNew, isCreated;
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isAuthenticated;
    private String password;
    private String date;
    private String gender;
    private String phone;
    private String disabledType;
    private String job;
    private String address;
    private String Interests;
    private String profileImageURL;
    private String userType;
    public String getDisabledType() {
        return disabledType;
    }

    public void setDisabledType(String disabledType) {
        this.disabledType = disabledType;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInterests() {
        return Interests;
    }

    public void setInterests(String interests) {
        Interests = interests;
    }

    //    private Object idImage;
    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String date, String phone, String gender) {
        this.email = email;
    }

    public User(Parcel in) {
        this.email = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.password = in.readString();
        this.gender = in.readString();
        this.phone = in.readString();
        this.date = in.readString();

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

<<<<<<< HEAD
//    public Object getIdImage() {
//        return idImage;
//    }
//
//    public void setIdImage(Object idImage) {
//        this.idImage = idImage;
//    }
=======
    @SuppressWarnings("WeakerAccess")
    public String email;

    public boolean isAuthenticated;
   private String password;
   private String date;
   private String gender;
   private String phone;
>>>>>>> parent of 883a06e... request helper

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.date);
        dest.writeString(this.gender);
        dest.writeString(this.phone);


    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
