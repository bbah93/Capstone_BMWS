package com.nyc.polymerse.Profile_Creation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nyc.polymerse.R;

public class Prof_Create extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof__create);
        if (savedInstanceState == null){
            Name_Fragment name_fragment = new Name_Fragment();
            name_fragment.getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, name_fragment, Name_Fragment.TAG)
                    .commit();


        }

    }
}
