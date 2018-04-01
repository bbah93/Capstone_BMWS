package com.nyc.polymerse.My_Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyc.polymerse.R;

public class MyProfileActivity extends AppCompatActivity {

    //TODO: Add hide and show for fragments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        if (savedInstanceState == null) {
            MyProfile_Saved_Fragment profileSavedFragment = new MyProfile_Saved_Fragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.my_prof_fragment_container, profileSavedFragment, MyProfile_Saved_Fragment.TAG )
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    }

}
