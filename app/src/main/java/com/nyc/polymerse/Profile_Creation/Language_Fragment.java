package com.nyc.polymerse.Profile_Creation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.My_Profile.MyProfileActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;

import java.util.HashMap;
import java.util.Map;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Language_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static String TAG;
    Spinner fluent;
    Spinner learning;
    FloatingActionButton language_fab;
    private static final String PROF_CREATE_KEY = "profile_creator";
    SharedPreferences profile_creator;
    SharedPreferences.Editor editor;
    View rootView;
    RadioGroup known_RadioGroup, learn_RadioGroup;
    RadioButton known_RadioButton, learn_RadioButton;
    DatabaseReference databaseReference;
    String learnString;
    String knownString;


    public Language_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.language_frag, container, false);

        language_fab = rootView.findViewById(R.id.language_fab);
        known_RadioGroup = rootView.findViewById(R.id.known_RadioGroup);
        learn_RadioGroup = rootView.findViewById(R.id.learn_RadioGroup);

        profile_creator = getActivity().getApplicationContext().getSharedPreferences(PROF_CREATE_KEY, MODE_PRIVATE);
        editor = profile_creator.edit();
        finishCreate();
        setUpViews();

        return rootView;
    }

    public void setUpViews() {
        setLangKnown_Spinner();
        setLangLearn_Spinner();
    }

    public void finishCreate(){
        language_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID_01 = known_RadioGroup.getCheckedRadioButtonId();
                known_RadioButton = (RadioButton) rootView.findViewById(selectedID_01);

                int selectedID_02 = learn_RadioGroup.getCheckedRadioButtonId();
                learn_RadioButton = (RadioButton) rootView.findViewById(selectedID_02);

                if(knownString.equals("Choose your language")){
                    toastFluent();
                }
                if (known_RadioGroup.getCheckedRadioButtonId() == -1){
                    toastLevel();
                }
               if (learnString.equals("Choose your language")){
                    toastLearning();
               }
               if (learn_RadioGroup.getCheckedRadioButtonId() == -1){
                   toastLevel();
               }

               else if (!knownString.equals("Choose your language")&& !learnString.equals("Choose your language")){
                   editor.putString("fluent_level", known_RadioButton.getText().toString());
                   editor.putString("learning_level", learn_RadioButton.getText().toString());
                   editor.putString("fluent", knownString);
                   editor.putString("learning_lang", learnString);
                   editor.apply();


                   Map<String,String> langLearn = new HashMap<>();
                       Map<String,String> langTeach = new HashMap<>();
                       langLearn.put(learnString, learn_RadioButton.getText().toString());
                       langTeach.put(knownString, known_RadioButton.getText().toString());
                       UserSingleton.getInstance().getUser().setLangLearn(langLearn);
                       UserSingleton.getInstance().getUser().setLangTeach(langTeach);
                       databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS);

                       //this map is to set the db at one time in users;
                       Map<String, Object> createUser = new HashMap<>();
                       createUser.put(UserSingleton.getInstance().getUser().getuID(),UserSingleton.getInstance().getUser());
                       databaseReference.updateChildren(createUser);

                       Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                       startActivity(intent);
               }

            }
        });
    }

    public void setLangKnown_Spinner() {
        fluent = rootView.findViewById(R.id.known_input);
        ArrayAdapter<CharSequence> langAdapter = ArrayAdapter.createFromResource(getContext(), R.array.languages, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fluent.setAdapter(langAdapter);
        fluent.setOnItemSelectedListener(this);
    }


    public void setLangLearn_Spinner() {
        learning = rootView.findViewById(R.id.lang_learn_input);
        ArrayAdapter<CharSequence> learnAdapter = ArrayAdapter.createFromResource(getContext(), R.array.languages, android.R.layout.simple_spinner_item);
        learnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        learning.setAdapter(learnAdapter);
        learning.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.known_input) {
            knownString = parent.getItemAtPosition(position).toString();
        } else if (spinner.getId() == R.id.lang_learn_input) {
            learnString = parent.getItemAtPosition(position).toString();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void toastFluent() {
        Toast.makeText(getActivity().getApplicationContext(), "Please enter a language you know", Toast.LENGTH_LONG).show();
    }

    public void toastLearning() {
        Toast.makeText(getActivity().getApplicationContext(), "Please enter a language you are learning", Toast.LENGTH_LONG).show();
    }

    public void toastLevel() {
        Toast.makeText(getActivity().getApplicationContext(), "Please enter your fluency level", Toast.LENGTH_LONG).show();
    }
}


