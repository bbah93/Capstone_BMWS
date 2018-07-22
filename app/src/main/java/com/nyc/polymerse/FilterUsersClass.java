package com.nyc.polymerse;

import android.util.Log;

import com.nyc.polymerse.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wayne Kellman on 3/24/18.
 */

public class FilterUsersClass {
    //TODO: add the other shared prefs options to list when the profile spinners are updated

    private static final String TAG = "FilterUsersClass";

    public static ArrayList<User> filterUserBySharing(List<User> userList, User currentUser) {
        Log.d(TAG, "filterUserBySharing: ran");
        ArrayList<User> filteredUser = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            for (String languageOtherUsersAreSharing : userList.get(i).getLangTeach().keySet()) {
                if (currentUser.getLangLearn().containsKey(languageOtherUsersAreSharing)){
                    filteredUser.add(userList.get(i));
                    Log.d(TAG, "filterUserBySharing:  userList sharing lang " + languageOtherUsersAreSharing);
                }
            }
        }
        return filteredUser;
    }

    public static ArrayList<User> filterUserByLearning(List<User> userList, User currentUser) {
        Log.d(TAG, "filterUserByLearning: ran");
        ArrayList<User> filteredUser = new ArrayList<>();

        for (int i = 0; i < userList.size(); i++) {
            for (String languageOtherUsersAreLearning : userList.get(i).getLangLearn().keySet()) {
                if (currentUser.getLangTeach().containsKey(languageOtherUsersAreLearning)){
                    filteredUser.add(userList.get(i));
                    Log.d(TAG, "filterUserByLearning:  userList learning lang " + languageOtherUsersAreLearning);

                }
            }
        }
        return filteredUser;
    }
    public static ArrayList<User> filterUserByBeginner(List<User> userList) {
        ArrayList<User> userArrayList = new ArrayList<>();
        for (User u : userList.toArray(new User[userList.size()])) {
            //This is a case for when they are a beginner in only one.
            boolean beginner = true;
            Map<String, String> userLangLearnMap = u.getLangLearn();
            Map<String, String> userLangShareMap = u.getLangTeach();

            for (String learnFluency : userLangLearnMap.values()) {
                if (!learnFluency.equals("Beginner")){
                    userArrayList.add(u);
                    beginner = false;
                    Log.d(TAG, "filterUserByBeginner: learn beginner " + u.getUsername() );
                }
            }

            for (String shareFluency : userLangShareMap.values()) {
                if (!shareFluency.equals("Beginner")){
                    if (!beginner) {
                        if (!userArrayList.contains(u)) {
                            userArrayList.add(u);
                            Log.d(TAG, "filterUserByBeginner: share beginner " + u.getUsername());

                        }
                    }
                }
            }
        }

        return userArrayList;
    }
    public static ArrayList<User> filterUserByIntermediate(List<User> userList) {
        ArrayList<User> filteredUser = new ArrayList<>();
        for (User u : userList.toArray(new User[userList.size()])) {
            boolean intermediate = true;

            Map<String, String> userLangLearnMap = u.getLangLearn();
            Map<String, String> userLangShareMap = u.getLangTeach();

            for (String learnFluency : userLangLearnMap.values()) {
                if (!learnFluency.equals("Intermediate")){
                    filteredUser.add(u);
                    intermediate = false;
                }
            }

            for (String shareFluency : userLangShareMap.values()) {
                if (!shareFluency.equals("Intermediate")){
                    if (!intermediate) {
                        if (!filteredUser.contains(u)) {
                            filteredUser.add(u);
                        }
                    }
                }
            }
        }

        return filteredUser;
    }
    public static ArrayList<User> filterUserByAdvanced(List<User> userList) {
        ArrayList<User> filteredUser = new ArrayList<>();
        Log.d(TAG, "filterUserByAdvanced: ran");
        for (User u : userList.toArray(new User[userList.size()])) {
            boolean advanced = true;

            Map<String, String> userLangLearnMap = u.getLangLearn();
            Map<String, String> userLangShareMap = u.getLangTeach();

            for (String learnFluency : userLangLearnMap.values()) {
                if (!learnFluency.equals("Advanced")) {
                    filteredUser.add(u);
                    advanced = false;
                }
            }

            for (String shareFluency : userLangShareMap.values()) {
                if (!shareFluency.equals("Advanced")) {
                    if (!advanced) {
                        if (!filteredUser.contains(u)) {
                            userList.remove(u);
                        }
                    }
                }
            }
        }

        return filteredUser;
    }

    public static ArrayList<User> filterUserByFluent(List<User> userList) {
        ArrayList<User> filteredUser = new ArrayList<>();

        for (User u : userList.toArray(new User[userList.size()])) {
            Map<String, String> userLangLearnMap = u.getLangLearn();
            Map<String, String> userLangShareMap = u.getLangTeach();
            boolean fluent = true;

            for (String learnFluency : userLangLearnMap.values()) {
                if (!learnFluency.equals("Fluent")) {
                    filteredUser.add(u);
                    fluent = false;
                }
            }

            for (String shareFluency : userLangShareMap.values()) {
                if (!shareFluency.equals("Fluent")) {
                    if (!fluent) {
                        if (!filteredUser.contains(u)) {
                            filteredUser.add(u);
                        }
                    }
                }
            }
        }
        return filteredUser;
    }

    public static void incrementInvite(String key, Map<String, String> otherUserMap) {

        otherUserMap.put( "Invite" + (otherUserMap.size() + 1)  , key);
    }
}
