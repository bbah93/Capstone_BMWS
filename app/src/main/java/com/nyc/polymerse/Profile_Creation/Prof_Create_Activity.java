package com.nyc.polymerse.Profile_Creation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyc.polymerse.Constants;
import com.nyc.polymerse.R;


public class Prof_Create_Activity extends AppCompatActivity {

    //TODO:Add Transition Animation

    SharedPreferences profile_creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof__create);
        profile_creator = getApplicationContext().getSharedPreferences(Constants.PROF_CREATE_KEY,MODE_PRIVATE);

            Name_Fragment name_fragment = new Name_Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("Profile_create", Constants.PROF_CREATE_KEY);
            name_fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
//                    .add(R.id.fragment_container, name_fragment, Name_Fragment.TAG)
                    .replace(R.id.fragment_container, name_fragment, Name_Fragment.TAG)
                    .commit();


    }

}
