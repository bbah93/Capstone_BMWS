package com.nyc.polymerse.My_Profile;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_MyProfile_Fragment extends Fragment {

    //TODO: Add Spinner logic
    //TODO: Pull profile image url

    View rootView;
    FloatingActionButton saveEditsButton;
    EditText nameEdit;
    EditText locationEdit;
    EditText aboutMeEdit;
    Spinner langShareSpinner;
    Spinner langLearnSpinner;
    ProgressBar shareProgressBar;
    ProgressBar learnProgressBar;
    User user;

    public Edit_MyProfile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_my_profile_, container, false);

        saveEditsButton = rootView.findViewById(R.id.save_profile_fab);
        nameEdit = rootView.findViewById(R.id.Edit_Username);
        locationEdit = rootView.findViewById(R.id.Edit_UserLocation);
        aboutMeEdit = rootView.findViewById(R.id.aboutMe_EditTexts);
        langLearnSpinner = rootView.findViewById(R.id.prof_langLearn_spinner);
        langShareSpinner = rootView.findViewById(R.id.sharing_lang_spinner);
        shareProgressBar = rootView.findViewById(R.id.editMyProfile_learning_level);
        learnProgressBar = rootView.findViewById(R.id.myprof_sharing_level);
        user = UserSingleton.getInstance().getUser();

        Toast.makeText(getActivity().getApplicationContext(),"Edit Profile Mode", Toast.LENGTH_LONG).show();

        saveEditsClick();

        return rootView;
    }

    public void saveEditsClick(){
        saveEditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUsername(nameEdit.getText().toString());
                user.setAboutMe(aboutMeEdit.getText().toString());
                Map<String, String> langLearn = new HashMap<>();
                UserSingleton.getInstance().setUser(user);

                //This saves it to the database
                FirebaseDatabase.getInstance().getReference().child(Constants.USERS)
                        .child(user.getuID()).setValue(user);


                Edit_MyProfile_Fragment editFragment = new Edit_MyProfile_Fragment();
                MyProfile_Saved_Fragment profile_saved_fragment = new MyProfile_Saved_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.my_prof_fragment_container, profile_saved_fragment)
                        .commit();
            }
        });
    }
}
