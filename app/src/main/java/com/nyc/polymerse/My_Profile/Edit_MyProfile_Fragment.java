package com.nyc.polymerse.My_Profile;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.nyc.polymerse.Constants.PROF_CREATE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_MyProfile_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "ProfileTAG";

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

    CircleImageView profileAvatar;

    String learnString;
    String knownString;
    SharedPreferences profileDetails;
    SharedPreferences.Editor editor;
    DatabaseReference databaseReference;

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
        user = UserSingleton.getInstance().getUser();
        profileAvatar = rootView.findViewById(R.id.user_profile_avatar);

        profileDetails = getActivity().getSharedPreferences(PROF_CREATE_KEY, Context.MODE_PRIVATE);
        editor = profileDetails.edit();
        Toast.makeText(getActivity().getApplicationContext(),"Edit Profile Mode", Toast.LENGTH_LONG).show();

        saveEditsClick();
        setUpViews();
        grabProfileURL();

        return rootView;
    }

    public void saveEditsClick(){
        saveEditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUsername(nameEdit.getText().toString());
                user.setAboutMe(aboutMeEdit.getText().toString());
                UserSingleton.getInstance().setUser(user);

                Map<String,String> langLearn = new HashMap<>();
                Map<String,String> langTeach = new HashMap<>();
                langLearn.put(learnString, profileDetails.getString("learning_level", null)  );
                langTeach.put(knownString, profileDetails.getString("fluent_level", null) );

                UserSingleton.getInstance().getUser().setLangLearn(langLearn);
                UserSingleton.getInstance().getUser().setLangTeach(langTeach);
                databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS);
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

    public void setLangKnown_Spinner() {
        langShareSpinner = rootView.findViewById(R.id.prof_langLearn_spinner);
        ArrayAdapter<CharSequence> langAdapter = ArrayAdapter.createFromResource(getContext(), R.array.languages, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langShareSpinner.setAdapter(langAdapter);
        langShareSpinner.setOnItemSelectedListener(this);
    }


    public void setLangLearn_Spinner() {
        langLearnSpinner = rootView.findViewById(R.id.sharing_lang_spinner);
        ArrayAdapter<CharSequence> learnAdapter = ArrayAdapter.createFromResource(getContext(), R.array.languages, android.R.layout.simple_spinner_item);
        learnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langLearnSpinner.setAdapter(learnAdapter);
        langLearnSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;

        if (spinner.getId() == R.id.prof_langLearn_spinner) {
            knownString = parent.getItemAtPosition(position).toString();
            AlertDialog.Builder alertDialogue = new AlertDialog.Builder(getActivity());
            alertDialogue.setTitle("Choose Your Fluency Level");
            String [] fluencyLevels = {"Beginner", "Intermediate", "Advanced", "Fluent"};

            int checkedItem = 0;
            alertDialogue.setSingleChoiceItems(fluencyLevels, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String [] fluencyLevels = {"Beginner", "Intermediate", "Advanced", "Fluent"};
                    String learningLevel = fluencyLevels[which];
                    editor.putString("learning_level", learningLevel);
                    editor.putString("learning_lang", learnString);
                    editor.apply();
                }
            });

            alertDialogue.setNegativeButton("Cancel", null);

            AlertDialog dialog = alertDialogue.create();
            dialog.show();

        } else if (spinner.getId() == R.id.sharing_lang_spinner) {
            learnString = parent.getItemAtPosition(position).toString();
            AlertDialog.Builder alertDialogue = new AlertDialog.Builder(getActivity());
            alertDialogue.setTitle("Choose Your Fluency Level");
            String [] fluencyLevels = {"Beginner", "Intermediate", "Advanced", "Fluent"};

            int checkedItem = 0;
            alertDialogue.setSingleChoiceItems(fluencyLevels, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alertDialogue.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String knownFluency = fluencyLevels[which];

                    editor.putString("fluent_level", knownFluency);
                    editor.putString("fluent", knownString);
                    editor.apply();

                }
            });

            alertDialogue.setNegativeButton("Cancel", null);

            AlertDialog dialog = alertDialogue.create();
            dialog.show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void grabProfileURL() {

        if (user != null) {
            if (user.getProfilePic() != null) {
                String imgUrl = user.getProfilePic();
                Log.d(TAG, "grabProfileURL: " + imgUrl);
                Picasso.get().load(imgUrl).fit().placeholder(R.drawable.ic_account_circle_black_24dp).into(profileAvatar);
            }
        }
    }

    public void setUpViews() {
        setLangKnown_Spinner();
        setLangLearn_Spinner();
    }

}
