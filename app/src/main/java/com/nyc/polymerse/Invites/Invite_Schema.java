package com.nyc.polymerse.Invites;

/**
 * Created by bobbybah on 3/24/18.
 */

public class Invite_Schema {

    String invite_ID;
    String sender_ID;
    String location;
    String time;
    String date;
    String receiver_ID;
    String acceptStatus;

    public void setInvite_ID(String invite_ID) {
        this.invite_ID = invite_ID;
    }

    public void setSender_ID(String sender_ID) {
        this.sender_ID = sender_ID;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReceiver_ID(String receiver_ID) {
        this.receiver_ID = receiver_ID;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }
}
