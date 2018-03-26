package com.nyc.polymerse.test;

import com.nyc.polymerse.FilterUsersClass;
import com.nyc.polymerse.User;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Wayne Kellman on 3/24/18.
 */
public class FilterUsersClassTest {
    @Test
    public void filterUserBySharingTest() throws Exception {
        User user = new User();
        Map<String, String> langTeach = new HashMap<>();
        langTeach.put("English", "Fluent");
        user.setLangTeach(langTeach);

        List<User> userList = new LinkedList<>();
        User otherUser1 = new User();
        User otherUser2 = new User();
        Map<String,String> langLearn = new HashMap<>();
        langLearn.put("English","Beginner");
        Map<String,String> langLearn2 = new HashMap<>();
        langLearn2.put("German", "Beginner");
        otherUser1.setLangLearn(langLearn);
        otherUser2.setLangLearn(langLearn2);
        userList.add(otherUser1);
        userList.add(otherUser2);

        List<User> filterdList = FilterUsersClass.filterUserBySharing(userList,user);

        assertNotEquals(userList.size(),filterdList.size());
    }

    @Test
    public void filterUserByLearningTst() throws Exception {

        User user = new User();
        Map<String, String> langLearn = new HashMap<>();
        langLearn.put("English", "Fluent");
        user.setLangLearn(langLearn);

        List<User> userList = new LinkedList<>();
        User otherUser1 = new User();
        User otherUser2 = new User();
        Map<String,String> langTeach1 = new HashMap<>();
        langTeach1.put("English","Beginner");
        Map<String,String> langTeach2 = new HashMap<>();
        langTeach2.put("German", "Beginner");
        otherUser1.setLangTeach(langTeach1);
        otherUser2.setLangTeach(langTeach2);
        userList.add(otherUser1);
        userList.add(otherUser2);

        List<User> filterList = FilterUsersClass.filterUserByLearning(userList,user);

        assertNotEquals(userList.size(),filterList.size());
    }

    @Test
    public void incrementInviteTest() {
        String key = "bfkbskf";
        Map<String,String> testMap = new HashMap<>();

        testMap.put("Invite1", "njksdbsak");
        testMap.put("Invite2", "nekjsbfek");
        FilterUsersClass.incrementInvite(key,testMap);

        assertNotEquals(2,testMap.size());
    }


}