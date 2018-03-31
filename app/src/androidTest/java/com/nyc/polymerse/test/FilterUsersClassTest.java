package com.nyc.polymerse.test;

import com.nyc.polymerse.FilterUsersClass;
import com.nyc.polymerse.User;

import org.junit.Test;

import java.util.ArrayList;
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
        Map<String, String> langLearn = new HashMap<>();
        langLearn.put("English", "Fluent");
        user.setLangLearn(langLearn);

        List<User> userList = new ArrayList<>();
        User otherUser1 = new User();
        User otherUser2 = new User();
        User otherUser3 = new User();


        Map<String,String> langShare1 = new HashMap<>();
        langShare1.put("English","Beginner");
        Map<String,String> langShare2 = new HashMap<>();
        langShare2.put("German", "Beginner");
        Map<String,String> langShare3 = new HashMap<>();
        langShare3.put("German", "Beginner");

        otherUser1.setLangTeach(langShare1);
        otherUser2.setLangTeach(langShare2);
        otherUser3.setLangTeach(langShare3);

        userList.add(otherUser1);

        List<User> filterList1 = FilterUsersClass.filterUserBySharing(userList,user);

        assertEquals(filterList1.size(), userList.size());


        userList.add(otherUser2);
        userList.add(otherUser3);

        List<User> filteredList2 = FilterUsersClass.filterUserBySharing(userList,user);

        assertNotEquals(userList.size(),filteredList2.size());
    }

    @Test
    public void filterUserByLearningTest() throws Exception {

        User user = new User();
        Map<String, String> langLearn = new HashMap<>();
        langLearn.put("English", "Fluent");
        user.setLangTeach(langLearn);

        List<User> userList = new ArrayList<>();
        User otherUser1 = new User();
        User otherUser2 = new User();
        User otherUser3 = new User();


        Map<String,String> langShare1 = new HashMap<>();
        langShare1.put("English","Beginner");
        Map<String,String> langShare2 = new HashMap<>();
        langShare2.put("German", "Beginner");
        Map<String,String> langShare3 = new HashMap<>();
        langShare3.put("German", "Beginner");

        otherUser1.setLangLearn(langShare1);
        otherUser2.setLangLearn(langShare2);
        otherUser3.setLangLearn(langShare3);

        userList.add(otherUser1);

        List<User> filterList1 = FilterUsersClass.filterUserByLearning(userList,user);

        assertEquals(filterList1.size(), userList.size());


        userList.add(otherUser2);
        userList.add(otherUser3);

        List<User> filteredList2 = FilterUsersClass.filterUserByLearning(userList,user);

        assertNotEquals(userList.size(),filteredList2.size());
    }

    @Test
    public void filterByBeginnerFluency() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        Map<String,String> userFluencyMap1 = new HashMap<>();
        Map<String,String> userFluencyMap2 = new HashMap<>();
        Map<String,String> userFluencyMap3 = new HashMap<>();
        Map<String,String> userFluencyMap4 = new HashMap<>();

        userFluencyMap1.put("German","Beginner");
        userFluencyMap2.put("Japanese","Intermediate");
        userFluencyMap3.put("Spanish","Advanced");
        userFluencyMap4.put("Mandarin","Fluent");

        user1.setLangLearn(userFluencyMap1);
        user1.setLangTeach(userFluencyMap1);
        user2.setLangLearn(userFluencyMap2);
        user2.setLangTeach(userFluencyMap2);
        user3.setLangLearn(userFluencyMap3);
        user3.setLangTeach(userFluencyMap3);
        user4.setLangLearn(userFluencyMap4);
        user4.setLangTeach(userFluencyMap4);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        List<User> filteredUserList = FilterUsersClass.filterUserByBeginner(userList);

        assertNotEquals(userList.size(),filteredUserList.size());
        assertEquals(userList.size() ,filteredUserList.size() + 1);



    }
    @Test
    public void filterByIntermediateFluency() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        Map<String,String> userFluencyMap1 = new HashMap<>();
        Map<String,String> userFluencyMap2 = new HashMap<>();
        Map<String,String> userFluencyMap3 = new HashMap<>();
        Map<String,String> userFluencyMap4 = new HashMap<>();

        userFluencyMap1.put("German","Beginner");
        userFluencyMap2.put("Japanese","Intermediate");
        userFluencyMap3.put("Spanish","Advanced");
        userFluencyMap4.put("Mandarin","Fluent");

        user1.setLangLearn(userFluencyMap1);
        user1.setLangTeach(userFluencyMap1);
        user2.setLangLearn(userFluencyMap2);
        user2.setLangTeach(userFluencyMap2);
        user3.setLangLearn(userFluencyMap3);
        user3.setLangTeach(userFluencyMap3);
        user4.setLangLearn(userFluencyMap4);
        user4.setLangTeach(userFluencyMap4);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        List<User> filteredUserList = FilterUsersClass.filterUserByIntermediate(userList);

        assertNotEquals(userList.size(),filteredUserList.size());
        assertEquals(userList.size() ,filteredUserList.size() + 1);

    }
    @Test
    public void filterByAdvancedFluency() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        Map<String,String> userFluencyMap1 = new HashMap<>();
        Map<String,String> userFluencyMap2 = new HashMap<>();
        Map<String,String> userFluencyMap3 = new HashMap<>();
        Map<String,String> userFluencyMap4 = new HashMap<>();

        userFluencyMap1.put("German","Beginner");
        userFluencyMap2.put("Japanese","Intermediate");
        userFluencyMap3.put("Spanish","Advanced");
        userFluencyMap4.put("Mandarin","Fluent");

        user1.setLangLearn(userFluencyMap1);
        user1.setLangTeach(userFluencyMap1);
        user2.setLangLearn(userFluencyMap2);
        user2.setLangTeach(userFluencyMap2);
        user3.setLangLearn(userFluencyMap3);
        user3.setLangTeach(userFluencyMap3);
        user4.setLangLearn(userFluencyMap4);
        user4.setLangTeach(userFluencyMap4);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        List<User> filteredUserList = FilterUsersClass.filterUserByAdvanced(userList);

        assertNotEquals(userList.size(),filteredUserList.size());
        assertEquals(userList.size() ,filteredUserList.size() + 1);

    }
    @Test
    public void filterByFluentFluency() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();

        Map<String,String> userFluencyMap1 = new HashMap<>();
        Map<String,String> userFluencyMap2 = new HashMap<>();
        Map<String,String> userFluencyMap3 = new HashMap<>();
        Map<String,String> userFluencyMap4 = new HashMap<>();

        userFluencyMap1.put("German","Beginner");
        userFluencyMap2.put("Japanese","Intermediate");
        userFluencyMap3.put("Spanish","Advanced");
        userFluencyMap4.put("Mandarin","Fluent");

        user1.setLangLearn(userFluencyMap1);
        user1.setLangTeach(userFluencyMap1);
        user2.setLangLearn(userFluencyMap2);
        user2.setLangTeach(userFluencyMap2);
        user3.setLangLearn(userFluencyMap3);
        user3.setLangTeach(userFluencyMap3);
        user4.setLangLearn(userFluencyMap4);
        user4.setLangTeach(userFluencyMap4);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        List<User> filteredUserList = FilterUsersClass.filterUserByFluent(userList);

        assertNotEquals(userList.size(),filteredUserList.size());
        assertEquals(userList.size() ,filteredUserList.size() + 1);

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