package com.nyc.polymerse;

import java.util.ArrayList;

/**
 * Created by bobbybah on 4/1/18.
 */

public class DummyUser_Data {

    ArrayList<String> userImagesUrls = new ArrayList<>(10);


    public ArrayList<String> getUserImagesUrls() {
        userImagesUrls.add("https://randomuser.me/api/portraits/men/29.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/men/71.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/women/69.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/women/89.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/women/15.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/men/9.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/men/11.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/women/11.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/women/47.jpg");
        userImagesUrls.add("https://randomuser.me/api/portraits/women/80.jpg");

        return userImagesUrls;
    }
}
