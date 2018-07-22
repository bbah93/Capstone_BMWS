package com.nyc.polymerse.models;

import java.util.Map;

/**
 * Created by Wayne Kellman on 3/17/18.
 */

public class User {

    private String username;
    private String email;
    private String city;
    private String state;
    private Map<String, String> langLearn;
    private Map<String, String> langTeach;
    private String uID;
    private Map<String, String> invites;
    private Map<String,String> blocked;
    private String profilePic;
    private String aboutMe;

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }



    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, String> getLangLearn() {
        return langLearn;
    }

    public void setLangLearn(Map<String, String> langLearn) {
        this.langLearn = langLearn;
    }

    public Map<String, String> getLangTeach() {
        return langTeach;
    }

    public void setLangTeach(Map<String, String> langTeach) {
        this.langTeach = langTeach;
    }

    public Map<String, String> getInvites() {
        return invites;
    }

    public void setInvites(Map<String, String> invites) {
        this.invites = invites;
    }

    public Map<String, String> getBlocked() {
        return blocked;
    }

    public void setBlocked(Map<String, String> blocked) {
        this.blocked = blocked;
    }
}

