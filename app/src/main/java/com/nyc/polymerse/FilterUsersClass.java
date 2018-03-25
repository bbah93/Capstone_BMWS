package com.nyc.polymerse;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Wayne Kellman on 3/24/18.
 */

public class FilterUsersClass {

    private static final String TAG = "FilterUsersClass";

    public static ArrayList<User> filterUserBySharing(List<User> userList, User currentUser) {
        Log.d(TAG, "filterUserBySharing: ran");
        ArrayList<User> filteredUser = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            for (String languageOtherUsersAreLearning : userList.get(i).getLangLearn().keySet()) {
                if (currentUser.getLangTeach().containsKey(languageOtherUsersAreLearning)){
                    filteredUser.add(userList.get(i));
                    Log.d(TAG, "filterUserBySharing:  userlist sharing lang " + userList.get(i));
                }
            }
        }
        return filteredUser;
    }

    public static ArrayList<User> filterUserByLearning(List<User> userList, User currentUser) {
        Log.d(TAG, "filterUserByLearning: ran");
        ArrayList<User> filteredUser = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            for (String languageOtherUsersAreTeaching : userList.get(i).getLangTeach().keySet()) {
                if (currentUser.getLangLearn().containsKey(languageOtherUsersAreTeaching)){
                    filteredUser.add(userList.get(i));
                    Log.d(TAG, "filterUserByLearning:  userlist learning lang " + userList.get(i));

                }
            }
        }
        return filteredUser;
    }
}
