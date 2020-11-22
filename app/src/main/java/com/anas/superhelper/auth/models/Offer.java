package com.anas.superhelper.auth.models;

public class Offer {
    String sender;
    String receiver;
    String offerDetails;
    String hourPrice;

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    String senderPhoneNumber;
    public int getOfferNumberInTheList() {
        return offerNumberInTheList;
    }

    public void setOfferNumberInTheList(int offerNumberInTheList) {
        this.offerNumberInTheList = offerNumberInTheList;
    }

    int offerNumberInTheList;
    public String getSenderProfileImageURl() {
        return senderProfileImageURl;
    }

    public void setSenderProfileImageURl(String senderProfileImageURl) {
        this.senderProfileImageURl = senderProfileImageURl;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    String senderProfileImageURl;
String senderName;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    public String getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(String offerTime) {
        this.offerTime = offerTime;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    String offerTime;
String offerDate;
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getOfferDetails() {
        return offerDetails;
    }

    public void setOfferDetails(String offerDetails) {
        this.offerDetails = offerDetails;
    }

    public String getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(String hourPrice) {
        this.hourPrice = hourPrice;
    }
}
