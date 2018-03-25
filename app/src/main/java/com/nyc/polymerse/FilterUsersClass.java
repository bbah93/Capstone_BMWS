package com.nyc.polymerse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wayne Kellman on 3/24/18.
 */

public class FilterUsersClass {

    public static List<User> filterUserBySharing(List<User> userList, User currentUser) {
        List<User> fileredUser = new LinkedList<>();

        for (int i = 0; i < userList.size(); i++) {
            for (String languageOtherUsersAreLearning : userList.get(i).getLangLearn().keySet()) {
                if (currentUser.getLangTeach().containsKey(languageOtherUsersAreLearning)){
                    fileredUser.add(userList.get(i));
                }
            }
        }
        return fileredUser;
    }

    public static List<User> filterUserByLearning(List<User> userList, User currentUser) {
        List<User> filteredUser = new LinkedList<>();

        for (int i = 0; i < userList.size(); i++) {
            for (String languageOtherUsersAreTeaching : userList.get(i).getLangTeach().keySet()) {
                if (currentUser.getLangLearn().containsKey(languageOtherUsersAreTeaching)){
                    filteredUser.add(userList.get(i));
                }
            }
        }
        return filteredUser;
    }
}
