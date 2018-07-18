package com.nyc.polymerse;


import com.nyc.polymerse.models.User;

/**
 * Created by Wayne Kellman on 3/20/18.
 */

public class UserSingleton {
    private User user;
    private static UserSingleton userSingleton;
    private String firebaseUid;

    public static UserSingleton getInstance() {
        if (userSingleton == null) {
            userSingleton = new UserSingleton();
            return userSingleton;
        }
        return userSingleton;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }
}
