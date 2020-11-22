package com.anas.superhelper.auth.models;

public class RequestHelper {
    String relevantTags;
    String requestTitle;
    String requestDetails;
    String whoIsTheHelpFor;
    String whatYouNeedHelpWith;
    String latitude;
    String longitude;
    int uid;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    String senderId;
    String requestOwnerId;

    public RequestHelper() {

    }

    public RequestHelper(String relevantTags, String requestTitle, String requestDetails, String whoIsTheHelpFor, String whatYouNeedHelpWith, String latitude, String longitude) {
        this.relevantTags = relevantTags;
        this.requestTitle = requestTitle;
        this.requestDetails = requestDetails;
        this.whoIsTheHelpFor = whoIsTheHelpFor;
        this.whatYouNeedHelpWith = whatYouNeedHelpWith;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRequestOwnerId() {
        return requestOwnerId;
    }

    public void setRequestOwnerId(String requestOwnerId) {
        this.requestOwnerId = requestOwnerId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRelevantTags() {
        return relevantTags;
    }

    public void setRelevantTags(String relevantTags) {
        this.relevantTags = relevantTags;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

    public String getWhoIsTheHelpFor() {
        return whoIsTheHelpFor;
    }

    public void setWhoIsTheHelpFor(String whoIsTheHelpFor) {
        this.whoIsTheHelpFor = whoIsTheHelpFor;
    }

    public String getWhatYouNeedHelpWith() {
        return whatYouNeedHelpWith;
    }

    public void setWhatYouNeedHelpWith(String whatYouNeedHelpWith) {
        this.whatYouNeedHelpWith = whatYouNeedHelpWith;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
