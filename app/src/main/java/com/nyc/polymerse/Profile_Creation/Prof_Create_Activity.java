package com.nyc.polymerse.Profile_Creation;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyc.polymerse.R;

import java.util.ArrayList;
import java.util.List;

public class Prof_Create_Activity extends AppCompatActivity {
    //TODO:Give Language and Location Fragments spinners instead of EditTexts
    //TODO:Add Transition Animation to fragment swipes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof__create);


    }

}
