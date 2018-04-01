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

import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_MyProfile_Fragment extends Fragment {

    View rootView;
    FloatingActionButton saveEditsButton;
    EditText nameEdit;
    EditText locationEdit;
    EditText aboutMeEdit;
    Spinner langShareSpinner;
    Spinner langLearnSpinner;
    ProgressBar shareProgressBar;
    ProgressBar learnProgressBar;

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

        saveEditsClick();

        return rootView;
    }

    public void saveEditsClick(){
        saveEditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_MyProfile_Fragment editFragment = new Edit_MyProfile_Fragment();
                MyProfile_Saved_Fragment profile_saved_fragment = new MyProfile_Saved_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .hide(editFragment)
                        .show(profile_saved_fragment)
                        .commit();
            }
        });
    }
}
